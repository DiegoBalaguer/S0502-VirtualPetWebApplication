package com.virtualgame.entites.petEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PetCreateDto(
        @NotBlank(message = "Name is required")
        @Schema(description = "PetEntity name", example = "Gatito")
        String name,

        @Schema(description = "PetEntity imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl
) {}