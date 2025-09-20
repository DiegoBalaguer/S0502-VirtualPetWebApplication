package com.virtualgame.entites.userEntity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordDto(
        @Schema(description = "User's old password in the system.", example = "mi_contraseña")
        @NotBlank(message = "Old password cannot be empty")
        @Size(min = 4, message = "Old password must be at least 4 characters")
        String oldPassword,

        @Schema(description = "User's new password.", example = "mi_contraseña_nueva")
        @NotBlank(message = "New password cannot be empty")
        @Size(min = 4, message = "New password must be at least 4 characters")
        String newPassword,

        @Schema(description = "User's new password retype.", example = "mi_contraseña_nueva")
        @NotBlank(message = "New password retype cannot be empty")
        @Size(min = 4, message = "New password retype must be at least 4 characters")
        String newPasswordRetype) {}
