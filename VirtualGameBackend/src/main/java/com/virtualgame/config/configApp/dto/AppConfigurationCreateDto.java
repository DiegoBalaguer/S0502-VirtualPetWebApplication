package com.virtualgame.config.configApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public record AppConfigurationCreateDto(
        @NotBlank(message = "key name is required")
        @Schema(description = "Configuration key name.", example = "defaultPetHappy")
        String keyName,

        @NotBlank(message = "Key type is required")
        @Schema(description = "Configuration key type.", example = "INTEGER")
        @Enumerated(EnumType.STRING)
        String keyType,

        @NotBlank(message = "key value is required")
        @Schema(description = "Configuration key value.", example = "10")
        String keyValue,

        @Schema(description = "key Description value.", example = "Default value for pet hapy under creation")
        String keyDescription
) {}
