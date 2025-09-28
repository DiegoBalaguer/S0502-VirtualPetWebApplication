package com.virtualgame.entites.petEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetUpdateDto(
        @Schema(description = "PetEntity identifier", example = "1001")
        Long id,

        @Schema(description = "PetEntity name", example = "Gatitos")
        String name,

        @Schema(description = "PetEntity imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl
) {}