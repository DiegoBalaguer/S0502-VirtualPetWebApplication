package com.virtualgame.entites.petHabitat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record PetHabitatRespAdminDto(
        @Schema(description = "PetHabitat identifier", example = "1001")
        Long id,

        @Schema(description = "PetHabitat parent identifier.", example = "1001")
        Long parentId,

        @Schema(description = "PetHabitat name", example = "RUN")
        String name,

        @Schema(description = "PetHabitat imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "PetHabitat happy.", example = "5")
        Integer happy,
        @Schema(description = "PetHabitat tired.", example = "-5")
        Integer tired,
        @Schema(description = "PetHabitat hungry.", example = "10")
        Integer hungry,
        @Schema(description = "PetHabitat months.", example = "1")
        Integer months,
        @Schema(description = "PetHabitat age.", example = "0")
        Integer age,
        @Schema(description = "Minium age for petHabitat age.", example = "0")
        Integer ageMin,
        @Schema(description = "Date when created", example = "2025-07-15")
        LocalDateTime createdAt,

        @Schema(description = "User ID that created the habitat", example = "1001")
        Long createdBy,

        @Schema(description = "Date when updated", example = "2025-07-15")
        LocalDateTime updatedAt,

        @Schema(description = "User ID that updated", example = "1001")
        Long updatedBy,

        @Schema(description = "Date when the pet action was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,

        @Schema(description = "User ID that deleted the pet action.", example = "1001")
        Long deletedBy


) {
}