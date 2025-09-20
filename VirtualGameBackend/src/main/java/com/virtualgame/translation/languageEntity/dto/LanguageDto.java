package com.virtualgame.translation.languageEntity.dto;

import java.time.LocalDateTime;

public record LanguageDto (
    Long id,
    String code,
    String name,
    String nativeName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long updatedBy) {
}