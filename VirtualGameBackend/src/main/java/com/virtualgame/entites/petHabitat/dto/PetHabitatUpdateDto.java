package com.virtualgame.entites.petHabitat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetHabitatUpdateDto(
        @Schema(description = "PetHabitat identifier", example = "1001")
        Long id,

        @Schema(description = "PetHabitat name", example = "RUN")
        String name,

        @Schema(description = "PetHabitat imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,
        @Schema(description = "PetHabitat happy.", example = "5")
        Integer happy,
        @Schema(description = "PetHabitat tired.", example = "-5")
        Integer tired,
        @Schema(description = "PetHabitat hungry.", example = "10")
        Integer hungry
) {}