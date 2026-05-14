package com.gongback.domain.noshow.service;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Layer 3: 외부 상황 변수 기반 컨텍스트 리스크 점수 산출 (0~100)
 * - 수업까지 남은 시간, 요일, 수업 시간대를 반영
 */
@Component
public class ContextScorer {

    public double score(LocalDateTime scheduledAt) {
        LocalDateTime now = LocalDateTime.now();
        long hoursUntil = ChronoUnit.HOURS.between(now, scheduledAt);

        double riskScore = 25.0; // 기본값

        riskScore += timeUntilRisk(hoursUntil);
        riskScore += dayOfWeekRisk(scheduledAt.getDayOfWeek());
        riskScore += timeOfDayRisk(scheduledAt.getHour());

        return Math.max(0.0, Math.min(100.0, riskScore));
    }

    // 수업까지 남은 시간이 짧을수록 위험 (당일 취소/노쇼 가능성 ↑)
    private double timeUntilRisk(long hoursUntil) {
        if (hoursUntil < 0) return 0.0; // 이미 지난 수업
        if (hoursUntil < 2) return 35.0;
        if (hoursUntil < 6) return 20.0;
        if (hoursUntil < 24) return 5.0;
        if (hoursUntil < 72) return 0.0;
        return -5.0;
    }

    // 주중 초반(월) 및 주말 예약은 변수가 많아 리스크 소폭 상승
    private double dayOfWeekRisk(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> 10.0;
            case FRIDAY, SATURDAY, SUNDAY -> 5.0;
            default -> 0.0;
        };
    }

    // 이른 아침·심야 시간대는 변심 가능성 상승
    private double timeOfDayRisk(int hour) {
        if (hour >= 6 && hour < 8) return 15.0;
        if (hour >= 21) return 10.0;
        if (hour >= 12 && hour < 14) return -5.0;
        return 0.0;
    }
}
