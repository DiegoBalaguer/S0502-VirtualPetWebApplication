package com.virtualgame.entites.petEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PetRespAdminDto(
        @Schema(description = "Pet's entity identifier", example = "1001")
        Long id,

        @Schema(description = "Pet's entity name", example = "Gatitos")
        String name,

        @Schema(description = "Pet's entity imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "Date when created", example = "2025-07-15")
        LocalDateTime createdAt,

        @Schema(description = "Date when updated", example = "2025-07-15")
        LocalDateTime updatedAt,

        @Schema(description = "User ID that updated", example = "1001")
        Long updatedBy,

        @Schema(description = "Date when the pet entity was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,

        @Schema(description = "User ID that deleted the pet entity.", example = "1001")
        Long deletedBy
) {}