package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetUserUpdateDto(
        @Schema(description = "PetUser identifier", example = "1001")
        Long id,

        @Schema(description = "PetUser name", example = "Esther")
        String name,

        @Schema(description = "PetUser type ID", example = "1")
        Long petTypeId,

        @Schema(description = "User's PetUser proprietary ID", example = "1")
        Long userId,

        @Schema(description = "PetUser imageUrl",  example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "Pet action Id", example = "1")
        Long petActionId,

        @Schema(description = "Pet habitat Id", example = "1")
        Long petHabitatId,

        @Schema(description = "PetUser happiness", example = "75")
        Integer happy,

        @Schema(description = "PetUser tiredness", example = "75")
        Integer tired,

        @Schema(description = "PetUser hunger", example = "50")
        Integer hungry
) {}