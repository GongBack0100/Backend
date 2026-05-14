package com.gongback.domain.classitem.dto;

import com.gongback.domain.classitem.ClassCategory;
import com.gongback.domain.classitem.ClassItem;

public record ClassResponse(
        Long id,
        String title,
        String studio,
        ClassCategory category,
        Integer price,
        Integer capacity,
        Integer durationMinutes,
        Double rating,
        Integer reviewCount,
        String locationAddress,
        Double lat,
        Double lng,
        String imageUrl,
        String emoji,
        String tags,
        String recurringSchedule,
        Long hostId,
        String hostName
) {
    public static ClassResponse from(ClassItem c) {
        return new ClassResponse(
                c.getId(), c.getTitle(), c.getStudio(), c.getCategory(),
                c.getPrice(), c.getCapacity(), c.getDurationMinutes(),
                c.getRating(), c.getReviewCount(), c.getLocationAddress(),
                c.getLat(), c.getLng(), c.getImageUrl(), c.getEmoji(),
                c.getTags(), c.getRecurringSchedule(),
                c.getHost().getId(), c.getHost().getName()
        );
    }
}
