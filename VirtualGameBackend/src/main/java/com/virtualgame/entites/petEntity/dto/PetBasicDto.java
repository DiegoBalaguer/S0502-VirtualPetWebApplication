package com.virtualgame.entites.petEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetBasicDto(
        @Schema(description = "Pet's entity identifier", example = "1001")
        Long id,

        @Schema(description = "Pet's entity name", example = "Gatitos")
        String name,

        @Schema(description = "Pet's entity imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl
) {}