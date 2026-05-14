package com.gongback.domain.user.dto;

import com.gongback.domain.user.User;
import com.gongback.domain.user.UserRole;

public record UserResponse(
        Long id,
        String email,
        String name,
        UserRole role,
        Double trustScore,
        String avatarUrl
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getTrustScore(),
                user.getAvatarUrl()
        );
    }
}
