package com.virtualgame.entites.userEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateUserDto(
        @Schema(description = "User's identifier in the system.", example = "1001")
        Long id,
        @Schema(description = "User's username.", example = "Manuel")
        String username,
        @Schema(description = "User's email.", example = "user@correo.me")
        String email,
        @Schema(description = "User's imageUrl.", example = "https://i.pravatar.cc/200?img=46")
        String imageUrl) {
}
