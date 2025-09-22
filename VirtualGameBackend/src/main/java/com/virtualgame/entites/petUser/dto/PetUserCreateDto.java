package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetUserCreateDto(
        @NotBlank(message = "Name is required")
        @Schema(description = "Pet User name", example = "Maravilla")
        String name,

        @NotNull(message = "Pet type is required")
        @Schema(description = "Pet user type ID", example = "1")
        Long petTypeId,

        @NotNull(message = "User entity proprietary userPetEntity is required")
        @Schema(description = "User user's PetEntity proprietary ID", example = "1")
        Long userId,

        @Schema(description = "Pet user's imageUrl", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl
) {}