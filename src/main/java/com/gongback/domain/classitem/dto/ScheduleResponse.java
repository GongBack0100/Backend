package com.gongback.domain.classitem.dto;

import com.gongback.domain.classitem.ClassSchedule;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        LocalDateTime scheduledAt,
        Integer maxCapacity,
        Integer bookedCount,
        Integer remainingSeats,
        Boolean isActive
) {
    public static ScheduleResponse from(ClassSchedule s) {
        return new ScheduleResponse(
                s.getId(), s.getScheduledAt(), s.getMaxCapacity(),
                s.getBookedCount(), s.remainingSeats(), s.getIsActive()
        );
    }
}
