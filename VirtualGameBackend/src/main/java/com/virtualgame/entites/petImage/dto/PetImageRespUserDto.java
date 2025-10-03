package com.virtualgame.entites.petImage.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetImageRespUserDto(
        @Schema(description = "PetImage identifier in the system.", example = "1001")
        Long id,
        @Schema(description = "PetImage parent identifier.", example = "1001")
        Long petId,
        @Schema(description = "PetImage age.", example = "0")
        Integer age,
        @Schema(description = "PetImage imageUrl.", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl) {}