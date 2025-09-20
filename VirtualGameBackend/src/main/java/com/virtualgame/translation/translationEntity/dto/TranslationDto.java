package com.virtualgame.translation.translationEntity.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationDto {
    private Long id;
    private Long languageId;
    private String languageCode;
    private Long messageId;
    private String messageKey;
    private String translatedText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}