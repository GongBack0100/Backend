package com.gongback.domain.noshow;

import com.gongback.common.ApiResponse;
import com.gongback.domain.noshow.dto.NoShowPredictionResponse;
import com.gongback.domain.noshow.dto.ScheduleRiskOverviewResponse;
import com.gongback.domain.noshow.service.NoShowPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/noshow")
@RequiredArgsConstructor
public class NoShowPredictionController {

    private final NoShowPredictionService noShowPredictionService;

    /**
     * 특정 예약의 노쇼 확률 예측
     * GET /api/noshow/predict/{bookingId}
     */
    @GetMapping("/predict/{bookingId}")
    public ResponseEntity<ApiResponse<NoShowPredictionResponse>> predict(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.ok(noShowPredictionService.predictByBookingId(bookingId)));
    }

    /**
     * 특정 클래스 일정 전체 예약의 노쇼 리스크 현황 (점주 대시보드용)
     * GET /api/noshow/schedule/{scheduleId}
     */
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleRiskOverviewResponse>> scheduleOverview(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(ApiResponse.ok(noShowPredictionService.predictByScheduleId(scheduleId)));
    }

    /**
     * 노쇼 처리 (실제 노쇼 발생 기록 — 모델 데이터 누적용)
     * POST /api/noshow/mark/{bookingId}
     */
    @PostMapping("/mark/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> markNoShow(@PathVariable Long bookingId) {
        noShowPredictionService.markNoShow(bookingId);
        return ResponseEntity.ok(ApiResponse.ok("노쇼 처리가 완료되었습니다."));
    }
}
