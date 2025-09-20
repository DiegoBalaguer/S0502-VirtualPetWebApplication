package com.virtualgame.config.configApp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "virtual_game_app_configuration")
public class AppConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Configuration key ID.", example = "1001")
    private Long id;

    @Schema(description = "Configuration key name.", example = "defaultPetHappy")
    private String keyName;

    @Schema(description = "Configuration key type.", example = "INTEGER")
    @Enumerated(EnumType.STRING)
    private KeyTypeEnum keyType;

    @Schema(description = "Configuration key value.", example = "10")
    private String keyValue;
}