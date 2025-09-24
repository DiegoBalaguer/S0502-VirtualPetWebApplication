package com.virtualgame.entites.petUser.dto;

import java.time.LocalDateTime;

public record PetUserValidationDto(
        Long userIdAuth,
        Long PetUserUserId,
        LocalDateTime deathDate,
        Integer age,
        Integer ageMin,
        String action
) {
}