package com.gongback.domain.noshow.service;

import com.gongback.common.CustomException;
import com.gongback.common.ErrorCode;
import com.gongback.domain.booking.Booking;
import com.gongback.domain.booking.BookingRepository;
import com.gongback.domain.classitem.ClassSchedule;
import com.gongback.domain.classitem.ClassScheduleRepository;
import com.gongback.domain.noshow.dto.NoShowPredictionResponse;
import com.gongback.domain.noshow.dto.ScheduleRiskOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoShowPredictionService {

    private static final double LAYER1_WEIGHT = 0.5;
    private static final double LAYER2_WEIGHT = 0.3;
    private static final double LAYER3_WEIGHT = 0.2;

    private final BookingRepository bookingRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ReliabilityScorer reliabilityScorer;
    private final OpenAiTextAnalyzer openAiTextAnalyzer;
    private final ContextScorer contextScorer;

    @Transactional(readOnly = true)
    public NoShowPredictionResponse predictByBookingId(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKING_NOT_FOUND));
        return predict(booking);
    }

    @Transactional(readOnly = true)
    public ScheduleRiskOverviewResponse predictByScheduleId(Long scheduleId) {
        ClassSchedule schedule = classScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLASS_NOT_FOUND));

        List<NoShowPredictionResponse> predictions = bookingRepository.findByClassSchedule(schedule)
                .stream()
                .map(this::predict)
                .toList();

        long high   = predictions.stream().filter(p -> "HIGH".equals(p.getRiskLevel())).count();
        long medium = predictions.stream().filter(p -> "MEDIUM".equals(p.getRiskLevel())).count();
        long low    = predictions.stream().filter(p -> "LOW".equals(p.getRiskLevel())).count();

        return ScheduleRiskOverviewResponse.builder()
                .scheduleId(scheduleId)
                .totalBookings(predictions.size())
                .highRiskCount((int) high)
                .mediumRiskCount((int) medium)
                .lowRiskCount((int) low)
                .predictions(predictions)
                .build();
    }

    @Transactional
    public void markNoShow(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKING_NOT_FOUND));
        booking.markNoShow();
    }

    private NoShowPredictionResponse predict(Booking booking) {
        double layer1 = reliabilityScorer.score(booking.getUser());

        // GPT 호출 1회로 score + summary 동시 획득
        OpenAiTextAnalyzer.TextAnalysisResult textResult = openAiTextAnalyzer.analyze(booking.getNotes());
        double layer2 = textResult.riskScore();

        double layer3 = contextScorer.score(booking.getClassSchedule().getScheduledAt());

        double rawScore   = layer1 * LAYER1_WEIGHT + layer2 * LAYER2_WEIGHT + layer3 * LAYER3_WEIGHT;
        double probability = Math.round(rawScore) / 100.0;

        return NoShowPredictionResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getName())
                .notes(booking.getNotes())
                .noShowProbability(probability)
                .riskLevel(toRiskLevel(probability))
                .reliabilityRiskScore(layer1)
                .textRiskScore(layer2)
                .contextRiskScore(layer3)
                .textAnalysisSummary(textResult.summary())
                .build();
    }

    private String toRiskLevel(double probability) {
        if (probability >= 0.6) return "HIGH";
        if (probability >= 0.3) return "MEDIUM";
        return "LOW";
    }
}
