package com.virtualgame.translation.translationEntity;

import com.virtualgame.translation.languageEntity.LanguageEntity;
import com.virtualgame.translation.messageEntity.MessageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "virtual_game_i18n_translations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"language_id", "message_id"})
})
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the translation", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private MessageEntity message;

    @Column(name = "translated_text", nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Translated text", example = "Bienvenido a nuestra aplicación")
    private String translatedText;

    @Schema(description = "Date when the translation was created", example = "2025-07-15T10:30:00")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Schema(description = "Date when the translation was updated", example = "2025-07-15T10:30:00")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Schema(description = "User ID that updated the translation", example = "1001")
    private Long updatedBy;

    @Schema(description = "Date when the translation was deleted with soft delete", example = "2025-07-15T10:30:00")
    private LocalDateTime deletedAt;

    @Schema(description = "User ID that deleted the translation", example = "1001")
    private Long deletedBy;
}