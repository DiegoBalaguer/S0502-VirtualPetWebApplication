package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record PetUserRespAdminDto(
        @Schema(description = "PetUser identifier", example = "1001")
        Long id,
        @Schema(description = "PetUser name.", example = "Esther")
        String name,
        @Schema(description = "Proprietary user PetUser identifier in the system.", example = "1001")
        Long userId,
        @Column(unique = true)
        @Schema(description = "PetUser type.", example = "1")
        Long petTypeId,
        @Schema(description = "PetUser photo Url.", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,
        @Schema(description = "Pet action's pets in the system.", example = "1")
        Long petActionId,
        @Schema(description = "PetUser habitat in the system.", example = "1")
        Long petHabitatId,
        @Schema(description = "PetUser happy.", example = "75")
        Integer happy,
        @Schema(description = "PetUser happy.", example = "75")
        Integer happyReps,
        @Schema(description = "PetUser tired.", example = "75")
        Integer tired,
        @Schema(description = "PetUser tired.", example = "75")
        Integer tiredReps,
        @Schema(description = "PetUser hungry.", example = "50")
        Integer hungry,
        @Schema(description = "PetUser hungry.", example = "50")
        Integer hungryReps,
        @Schema(description = "PetUser months.", example = "1")
        Integer months,
        @Schema(description = "PetUser age.", example = "0")
        Integer age,
        @Schema(description = "Date when the PetUser death.", example = "2025-07-15")
        LocalDateTime deathDate,
        @Schema(description = "Reason for death petUser.", example = "PetUser death for Age")
        String deathReason,
        @Schema(description = "Date when the PetUser was created.", example = "2025-07-15")
        LocalDateTime createdAt,
        @Schema(description = "User ID that created the PetUser", example = "1001")
        Long createdBy,
        @Schema(description = "Date when the PetUser was updated.", example = "2025-07-15")
        LocalDateTime updatedAt,
        @Schema(description = "User ID that updated the PetUser.", example = "1001")
        Long updatedBy,
        @Schema(description = "Date when the PetUser was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,
        @Schema(description = "User ID that deleted the PetUser.", example = "1001")
        Long deletedBy
) {
}