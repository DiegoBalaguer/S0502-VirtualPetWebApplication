package com.virtualgame.translation.languageEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LanguageUpdateDto(
    @Schema(description = "Code language", example = "es")
    String code,
    @Schema(description = "Name language name", example = "spanish")
    String name,
    @Schema(description = "Native name language name", example = "español")
    String nativeName) {
}