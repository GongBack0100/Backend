package com.gongback.domain.user.dto;

import com.gongback.domain.user.UserRole;

public record LoginResponse(
        String token,
        Long userId,
        String name,
        String email,
        UserRole role,
        Double trustScore,
        String avatarUrl
) {}
