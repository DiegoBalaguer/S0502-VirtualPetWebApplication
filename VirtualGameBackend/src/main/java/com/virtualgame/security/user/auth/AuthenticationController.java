package com.virtualgame.security.user.auth;

import com.virtualgame.security.user.auth.dto.AuthCreateUserRequestDto;
import com.virtualgame.security.user.auth.dto.AuthLoginRequestDto;
import com.virtualgame.security.user.auth.dto.AuthResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "API Manage Users Authorization", description = "API with generic methods for admin user entities.")
@Tag(name = "auth", description = "API to manage register and login users.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Create a new player in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or already exists"),
            @ApiResponse(responseCode = "409", description = "Conflict, Email already exists")
    })
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid AuthCreateUserRequestDto authCreateUserRequestDto) {

       return new ResponseEntity<>(authenticationService.createUser(authCreateUserRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDto) {

        return new ResponseEntity<>(authenticationService.loginUser(authLoginRequestDto), HttpStatus.OK);
    }
}

