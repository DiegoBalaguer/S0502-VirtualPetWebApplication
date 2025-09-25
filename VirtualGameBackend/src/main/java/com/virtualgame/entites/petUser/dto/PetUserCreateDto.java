package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetUserCreateDto(
        @NotBlank(message = "Name is required")
        @Schema(description = "PetUser name", example = "Mi Mascota")
        String name,

        @NotNull(message = "PetType is required")
        @Schema(description = "PetUser petTypeID", example = "1")
        Long petTypeId,

        @Schema(description = "PetUser imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl
) {}