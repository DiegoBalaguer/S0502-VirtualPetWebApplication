package com.virtualgame.security.user.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityUser {
    private Long userId;
    private String email;
    private String name;
}