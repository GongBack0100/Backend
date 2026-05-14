package com.gongback.domain.noshow.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScheduleRiskOverviewResponse {

    private Long scheduleId;
    private int totalBookings;
    private int highRiskCount;
    private int mediumRiskCount;
    private int lowRiskCount;
    private List<NoShowPredictionResponse> predictions;
}
