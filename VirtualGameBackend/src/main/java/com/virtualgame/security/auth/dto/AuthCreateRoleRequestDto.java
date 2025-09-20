package com.virtualgame.security.auth.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AuthCreateRoleRequestDto(

        @Size(max = 3, message = "The user cannot have more thant 3 roles")
        List<String> roleListName){
}
