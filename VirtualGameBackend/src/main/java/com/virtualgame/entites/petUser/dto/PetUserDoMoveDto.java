package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetUserDoMoveDto(
        @Schema(description = "PetUser identifier", example = "1001")
        Long id,

        @Schema(description = "Habitat Id move", example = "1")
        Long habitatId
) {}