package com.virtualgame.security.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SecurityUser {
    private Long userId;
    private String email;
    private String name;
    private String languageCode;
}