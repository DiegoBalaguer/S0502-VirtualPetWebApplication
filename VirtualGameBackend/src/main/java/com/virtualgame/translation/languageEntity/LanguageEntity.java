package com.virtualgame.translation.languageEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "virtual_game_i18n_languages")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the language", example = "1")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    @Schema(description = "Language code (e.g., en, es, fr)", example = "es")
    private String code;

    @Column(name = "name", nullable = false)
    @Schema(description = "Language name", example = "Spanish")
    private String name;

    @Column(name = "native_name")
    @Schema(description = "Native name of the language", example = "Español")
    private String nativeName;

    @Schema(description = "Date when the language was created", example = "2025-07-15T10:30:00")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Schema(description = "User ID that created the language", example = "1001")
    private Long createdBy;

    @Schema(description = "Date when the language was updated", example = "2025-07-15T10:30:00")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Schema(description = "User ID that updated the language", example = "1001")
    private Long updatedBy;

    @Schema(description = "Date when the language was deleted with soft delete", example = "2025-07-15T10:30:00")
    private LocalDateTime deletedAt;

    @Schema(description = "User ID that deleted the language", example = "1001")
    private Long deletedBy;
}