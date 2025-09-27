package com.virtualgame.security.user.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

    /*
    El error que verás será un MethodArgumentNotValidException, que se manejará automáticamente por Spring Boot y
    devolverá un Bad Request (400). El mensaje de error en el cuerpo de la respuesta indicará exactamente
    qué campo falló y por qué (por ejemplo, "Username cannot be empty" o "Password must be at least 4 characters").

    A nivel de base de datos, si el email o el email ya existen, la restricción de unicidad en la entidad
    UserEntity (@Column(unique = true)) causará un DataIntegrityViolationException.
     */


public record AuthCreateUserRequestDto(
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 4, message = "Password must be at least 4 characters")
        String password,

        @Null(message = "URL profile must be null or not blank")
        String  photoUrl,

        @NotNull(message = "Roles cannot be null")
        @Valid
        AuthCreateRoleRequestDto roleRequest) {
}
