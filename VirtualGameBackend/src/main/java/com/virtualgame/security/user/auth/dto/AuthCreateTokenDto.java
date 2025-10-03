package com.virtualgame.security.user.auth.dto;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

@Validated
public record AuthCreateTokenDto(
        Authentication authentication,
        Long userId,
        String userName,
        String userLanguage) {
}
