package com.virtualgame.entites.userEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserUpdateAdminDto(
        @Schema(description = "User's email.", example = "Manuel")
        String username,
        @Schema(description = "User's email.", example = "user@correo.me")
        String email,
        @Schema(description = "User's imageUrl.", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,
        @Schema(description = "Is user's account is enabled in the system.", example = "1")
        Boolean isEnabled,
        @Schema(description = "Is user's account is non-expired?", example = "1")
        Boolean accountNoExpired,
        @Schema(description = "Is user's account is non-locked?", example = "1")
        Boolean accountNoLocked,
        @Schema(description = "Is user's credential non-expired?", example = "1")
        Boolean credentialNoExpired,
        @Schema(description = "Date when the user was created.", example = "2025-07-15")
        LocalDateTime createdAt,
        @Schema(description = "Date when the user was updated.", example = "2025-07-15")
        LocalDateTime updatedAt,
        @Schema(description = "User ID that updated the user.", example = "1001")
        Long updatedBy,
        @Schema(description = "Date when the user was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,
        @Schema(description = "User ID that deleted the user.", example = "1001")
        Long deletedBy) {
}
