package com.virtualgame.config.configApp.dto;

import com.virtualgame.config.configApp.KeyTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record AppConfigurationUpdateDto(
        @Schema(description = "Configuration key ID.", example = "1001")
        Long id,

        @Schema(description = "Configuration key name.", example = "defaultPetHappy")
        String keyName,

        @Schema(description = "Configuration key type.", example = "INTEGER")
        @Enumerated(EnumType.STRING)
        KeyTypeEnum keyType,

        @Schema(description = "Configuration key value.", example = "10")
        String keyValue,

        @Schema(description = "Description value.", example = "Default value for pet happy under creation")
        String keyDescription
) {}