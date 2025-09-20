package com.virtualgame.translation.languageEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record LanguageCreateDto(
    @NotBlank(message = "Code language is required")
    @Schema(description = "Code language", example = "es")
    String code,
    @NotBlank(message = "Name is required")
    @Schema(description = "Name language name", example = "spanish")
    String name,
    @Schema(description = "Native name language name", example = "español")
    String nativeName,
    @Schema(description = "User ID create", example = "1001")
    Long updatedBy) {
}