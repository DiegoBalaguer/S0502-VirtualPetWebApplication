package com.virtualgame.security.user.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDto(
        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotBlank(message = "Password cannot be empty")
        String password) {}
