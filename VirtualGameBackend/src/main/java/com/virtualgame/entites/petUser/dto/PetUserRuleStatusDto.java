package com.virtualgame.entites.petUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetUserRuleStatusDto(
        @Schema(description = "Rule Status happy.", example = "5")
        Integer happy,
        @Schema(description = "Rule Status tired.", example = "-5")
        Integer tired,
        @Schema(description = "Rule Status hungry.", example = "10")
        Integer hungry,
        @Schema(description = "Rule Status months.", example = "1")
        Integer months,
        @Schema(description = "Rule Status life.", example = "0")
        Integer age,
        @Schema(description = "Rule Status min age.", example = "15")
        Integer ageMin
) {}