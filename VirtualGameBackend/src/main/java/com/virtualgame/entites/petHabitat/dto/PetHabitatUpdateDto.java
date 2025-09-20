package com.virtualgame.entites.petHabitat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetHabitatUpdateDto(
        @Schema(description = "Pet's action identifier", example = "1001")
        Long id,

        @Schema(description = "Pet's action name", example = "RUN")
        String name,

        @Schema(description = "Pet's action imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl,
        @Schema(description = "Pet's action happy.", example = "5")
        Integer happy,
        @Schema(description = "Pet's action tired.", example = "-5")
        Integer tired,
        @Schema(description = "Pet's action hungry.", example = "10")
        Integer hungry
) {}