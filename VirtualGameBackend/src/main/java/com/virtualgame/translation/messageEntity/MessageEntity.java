package com.virtualgame.translation.messageEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "virtual_game_i18n_messages")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the message", example = "1")
    private Long id;

    @Column(name = "message_key", unique = true, nullable = false)
    @Schema(description = "Message key/code", example = "welcome.message")
    private String messageKey;

    @Column(name = "description")
    @Schema(description = "Message description", example = "Welcome message for users")
    private String description;

    @Schema(description = "Date when the message was created", example = "2025-07-15T10:30:00")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Schema(description = "Date when the message was updated", example = "2025-07-15T10:30:00")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Schema(description = "User ID that updated the message", example = "1001")
    private Long updatedBy;

    @Schema(description = "Date when the message was deleted with soft delete", example = "2025-07-15T10:30:00")
    private LocalDateTime deletedAt;

    @Schema(description = "User ID that deleted the message", example = "1001")
    private Long deletedBy;
}