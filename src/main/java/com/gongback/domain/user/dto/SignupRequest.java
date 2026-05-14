package com.gongback.domain.user.dto;

import com.gongback.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "역할은 필수입니다.")
        UserRole role
) {}
