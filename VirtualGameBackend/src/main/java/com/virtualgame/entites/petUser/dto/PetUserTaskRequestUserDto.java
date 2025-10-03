package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PetUserTaskRequestUserDto(
        @NotNull(message = "PetUserId is required")
        @Schema(description = "PetUserId", example = "1")
        Long petUserId,

        @NotNull(message = "PetUserTaskId is required")
        @Schema(description = "ActionId or HabitatId for task", example = "1")
        Long petUserTaskId
) {
}