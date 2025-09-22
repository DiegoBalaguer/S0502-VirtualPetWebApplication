package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record PetUserRespAdminDto(
        @Schema(description = "Pet's identifier", example = "1001")
        Long id,
        @Schema(description = "Pet's name.", example = "Esther")
        String name,
        @Schema(description = "Proprietary user Pet's identifier in the system.", example = "1001")
        Long userId,
        @Column(unique = true)
        @Schema(description = "Pet's type.", example = "1")
        Long petTypeId,
        @Schema(description = "Pet's photo Url.", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,
        @Schema(description = "Pet action's pets in the system.", example = "1")
        Long petActionId,
        @Schema(description = "Pet's habitat in the system.", example = "1")
        Long petHabitatId,
        @Schema(description = "Pet's happy.", example = "75")
        Integer happy,
        @Schema(description = "Pet's happy.", example = "75")
        Integer happyReps,
        @Schema(description = "Pet's tired.", example = "75")
        Integer tired,
        @Schema(description = "Pet's tired.", example = "75")
        Integer tiredReps,
        @Schema(description = "Pet's hungry.", example = "50")
        Integer hungry,
        @Schema(description = "Pet's hungry.", example = "50")
        Integer hungryReps,
        @Schema(description = "Pet's months.", example = "1")
        Integer months,
        @Schema(description = "Pet's age.", example = "0")
        Integer age,
        @Schema(description = "Date when the pet death.", example = "2025-07-15")
        LocalDateTime deathDate,
        @Schema(description = "Date when the user was created.", example = "2025-07-15")
        LocalDateTime createdAt,
        @Schema(description = "User ID that created the user", example = "1001")
        Long createdBy,
        @Schema(description = "Date when the user was updated.", example = "2025-07-15")
        LocalDateTime updatedAt,
        @Schema(description = "User ID that updated the user.", example = "1001")
        Long updatedBy,
        @Schema(description = "Date when the user was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,
        @Schema(description = "User ID that deleted the user.", example = "1001")
        Long deletedBy
) {
}