package com.virtualgame.entites.petImage.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetImageRequestDto(
        @Schema(description = "PetImage parent identifier.", example = "1001")
        Long petId,
        @Schema(description = "PetImage age.", example = "0")
        Integer age) {}