package com.virtualgame.security.user.auth.dto;

public record AuthSecurityUserDto (
    Long userId,
    String email,
    String name,
    String languageCode) {}