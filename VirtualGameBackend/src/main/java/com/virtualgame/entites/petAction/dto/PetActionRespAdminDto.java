package com.virtualgame.entites.petAction.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PetActionRespAdminDto(
        @Schema(description = "PetAction identifier", example = "1001")
        Long id,

        @Schema(description = "PetAction name", example = "RUN")
        String name,

        @Schema(description = "PetAction imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "PetAction happy.", example = "5")
        Integer happy,
        @Schema(description = "PetAction tired.", example = "-5")
        Integer tired,
        @Schema(description = "PetAction hungry.", example = "10")
        Integer hungry,
        @Schema(description = "PetAction months.", example = "1")
        Integer months,
        @Schema(description = "PetAction life.", example = "0")
        Integer age,
        @Schema(description = "PetAction min age for action.", example = "15")
        Integer ageMin,

        @Schema(description = "Date when created", example = "2025-07-15")
        LocalDateTime createdAt,

        @Schema(description = "Date when updated", example = "2025-07-15")
        LocalDateTime updatedAt,

        @Schema(description = "User ID that updated", example = "1001")
        Long updatedBy,

        @Schema(description = "Date when the pet action was deleted with soft delete.", example = "2025-07-15")
        LocalDateTime deletedAt,

        @Schema(description = "User ID that deleted the pet action.", example = "1001")
        Long deletedBy
) {}