package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetUserDoActionDto(
        @Schema(description = "PetUser identifier", example = "1001")
        Long id,

        @Schema(description = "PetUser Action Id", example = "1")
        Long petActionId
) {}