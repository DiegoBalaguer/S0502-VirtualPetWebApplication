package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PetUserRespTaskAdminDto(
        @Schema(description = "PetUser identifier", example = "1001")
        Long id,

        @Schema(description = "PetUser name", example = "Esther")
        String name,

        @Schema(description = "PetUser type ID", example = "1")
        Long petTypeId,

        @Schema(description = "PetUser type name", example = "Logan 5")
        String petTypeName,

        @Schema(description = "User's PetEntity proprietary ID", example = "1")
        Long userId,

        @Schema(description = "PetUser imageUrl", example = "/active/images/citizens/logan-5.webp")
        String imageUrl,

        @Schema(description = "Pet action", example = "1")
        Long petActionId,

        @Schema(description = "PetUser action name", example = "PARTIES")
        String petActionName,

        @Schema(description = "Pet habitat", example = "1")
        Long petHabitatId,

        @Schema(description = "PetUser habitat name", example = "CLINICA")
        String petHabitatName,

        @Schema(description = "PetHabitat parentId", example = "1")
        Long petHabitatParentId,

        @Schema(description = "PetUser habitat imageUrl", example = "/images/habitats/clinica.webp")
        String petHabitatImageUrl,

        @Schema(description = "PetUser happiness", example = "75")
        Integer happy,

        @Schema(description = "PetUser happy.", example = "75")
        Integer happyReps,

        @Schema(description = "PetUser happy.", example = "75")
        Integer happyRepsMax,

        @Schema(description = "PetUser tiredness", example = "75")
        Integer tired,

        @Schema(description = "PetUser tired.", example = "75")
        Integer tiredReps,

        @Schema(description = "PetUser tired.", example = "75")
        Integer tiredMax,

        @Schema(description = "PetUser hunger", example = "50")
        Integer hungry,

        @Schema(description = "PetUser hungry.", example = "50")
        Integer hungryReps,

        @Schema(description = "PetUser hungry.", example = "50")
        Integer hungryRepsMax,

        @Schema(description = "PetUser months.", example = "1")
        Integer months,

        @Schema(description = "PetUser age.", example = "0")
        Integer age,

        @Schema(description = "Date when the pet death.", example = "2025-07-15")
        LocalDateTime deathDate,

        @Schema(description = "Reason for death petUser.", example = "PetUser death for Age")
        String deathReason
) {
}