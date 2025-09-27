package com.virtualgame.security.user.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDto(
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email cannot be empty")
        @JsonProperty("email") String email,

        @NotBlank(message = "Password cannot be empty")
        @JsonProperty("password") String password) {}
