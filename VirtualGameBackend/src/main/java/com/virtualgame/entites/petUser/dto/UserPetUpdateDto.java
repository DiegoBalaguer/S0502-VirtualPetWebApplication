package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserPetUpdateDto(
        @Schema(description = "Pet's identifier", example = "1001")
        Long id,

        @Schema(description = "Pet's name", example = "Maravilla")
        String name,

        @Schema(description = "Pet's type ID", example = "1")
        Long petTypeId,

        @Schema(description = "User's PetEntity proprietary ID", example = "1")
        Long userId,

        @Schema(description = "Pet's imageUrl",  example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,

        @Schema(description = "Pet action Id", example = "1")
        Long petActionId,

        @Schema(description = "Pet habitat Id", example = "1")
        Long petHabitatId,

        @Schema(description = "Pet's happiness", example = "75")
        Integer happy,

        @Schema(description = "Pet's tiredness", example = "75")
        Integer tired,

        @Schema(description = "Pet's hunger", example = "50")
        Integer hungry
) {}