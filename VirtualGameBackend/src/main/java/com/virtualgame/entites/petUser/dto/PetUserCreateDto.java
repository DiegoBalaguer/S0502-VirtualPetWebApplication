package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetUserCreateDto(
        @NotBlank(message = "Name is required")
        @Schema(description = "Pet's name", example = "Maravilla")
        String name,

        @NotNull(message = "Pet type is required")
        @Schema(description = "Pet's type ID", example = "1")
        Long petTypeId,

        @NotNull(message = "User entity proprietary userPetEntity is required")
        @Schema(description = "User's PetEntity proprietary ID", example = "1")
        Long userId,

        @Schema(description = "Pet's imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "Pet action", example = "1")
        Long petActionId,

        @Schema(description = "Pet habitat", example = "1")
        Long petHabitatId,

        @Schema(description = "Pet's happiness", example = "75")
        Integer happy,

        @Schema(description = "Pet's tiredness", example = "75")
        Integer tired,

        @Schema(description = "Pet's hunger", example = "50")
        Integer hungry
) {}