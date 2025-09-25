package com.virtualgame.entites.petAction.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetActionUpdateDto(
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
        Integer hungry
) {}