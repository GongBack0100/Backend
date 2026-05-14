package com.gongback.domain.noshow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoShowPredictionResponse {

    private Long bookingId;
    private Long userId;
    private String userName;
    private String notes;

    private double noShowProbability;   // 0.0 ~ 1.0
    private String riskLevel;           // LOW / MEDIUM / HIGH

    private double reliabilityRiskScore; // Layer 1: 0~100
    private double textRiskScore;        // Layer 2: 0~100
    private double contextRiskScore;     // Layer 3: 0~100

    private String textAnalysisSummary;
}
