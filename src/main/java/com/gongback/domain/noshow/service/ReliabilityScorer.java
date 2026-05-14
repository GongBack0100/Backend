package com.gongback.domain.noshow.service;

import com.gongback.domain.booking.BookingRepository;
import com.gongback.domain.booking.BookingStatus;
import com.gongback.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Layer 1: 정형 행동 데이터 기반 신뢰도 리스크 점수 산출 (0~100, 높을수록 위험)
 * - 과거 노쇼 이력, 취소 횟수, 사용자 신뢰 점수를 가중합산
 */
@Component
@RequiredArgsConstructor
public class ReliabilityScorer {

    private final BookingRepository bookingRepository;

    public double score(User user) {
        long total = bookingRepository.countByUser(user);

        if (total == 0) {
            // 신규 사용자: 신뢰 점수 기반 중립 위험도 반환
            return trustScoreRisk(user.getTrustScore());
        }

        long noShowCount = bookingRepository.countByUserAndStatus(user, BookingStatus.NOSHOW);
        long cancelCount = bookingRepository.countByUserAndStatus(user, BookingStatus.CANCELLED);

        double noShowRate = (double) noShowCount / total;
        double cancelRate = (double) cancelCount / total;

        // 히스토리 기반 위험도 (노쇼 비중 70%, 취소 비중 30%)
        double historyRisk = (noShowRate * 0.7 + cancelRate * 0.3) * 100.0;

        // 신뢰 점수 기반 보조 위험도 (1.0~5.0 → 0~100)
        double trustRisk = trustScoreRisk(user.getTrustScore());

        // 히스토리 70%, 신뢰 점수 30% 반영
        return Math.min(100.0, historyRisk * 0.7 + trustRisk * 0.3);
    }

    private double trustScoreRisk(double trustScore) {
        // trustScore: 1.0(최악) ~ 5.0(최고), 리스크는 반전
        return ((5.0 - trustScore) / 4.0) * 100.0;
    }
}
