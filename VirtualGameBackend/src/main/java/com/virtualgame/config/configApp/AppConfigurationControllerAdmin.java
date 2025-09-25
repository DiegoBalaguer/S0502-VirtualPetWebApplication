package com.virtualgame.config.configApp;

import com.virtualgame.config.configApp.dto.AppConfigurationCreateDto;
import com.virtualgame.config.configApp.dto.AppConfigurationRespAdminDto;
import com.virtualgame.config.configApp.dto.AppConfigurationUpdateDto;
import com.virtualgame.security.user.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/parameters")
@Tag(name = "API Manage parameters application (ADMIN)", description = "Endpoints for managing parameters application")
public class AppConfigurationControllerAdmin {

    private final AppConfigurationService appConfigurationService;

    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new app configuration", description = "Creates a new app configuration in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "app configuration created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<AppConfigurationRespAdminDto> create(
            @RequestBody @Valid AppConfigurationCreateDto createDto) {

        AppConfigurationRespAdminDto createdPetEntity = appConfigurationService.create(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetEntity);
    }

    @Operation(summary = "Find app configuration by ID", description = "Retrieves a specific app configuration by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "app configuration found"),
            @ApiResponse(responseCode = "404", description = "app configuration not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<AppConfigurationRespAdminDto> findPetEntityById(@PathVariable Long id) {
        AppConfigurationRespAdminDto petEntity = appConfigurationService.findAppConfigurationById(id);
        return ResponseEntity.ok(petEntity);
    }

    @Operation(summary = "Find all app configuration", description = "Retrieves all app configuration from the system")
    @ApiResponse(responseCode = "200", description = "List of app configuration retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<AppConfigurationRespAdminDto>> findAllPetEntities() {
        List<AppConfigurationRespAdminDto> appConfigurationRespAdminDto = appConfigurationService.findAllAppConfiguration();
        return ResponseEntity.ok(appConfigurationRespAdminDto);
    }

    @Operation(summary = "Update app configuration", description = "Updates an existing app configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "app configuration updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "app configuration not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<AppConfigurationRespAdminDto> updatePetEntity(
            @PathVariable Long id,
            @RequestBody @Valid AppConfigurationUpdateDto updateDto) {

        AppConfigurationRespAdminDto updatedPetEntity = appConfigurationService.updateAppConfiguration(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetEntity);
    }

    @Operation(summary = "Delete app configuration", description = "Permanently deletes a app configuration from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "app configuration deleted successfully"),
            @ApiResponse(responseCode = "404", description = "app configuration not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePetEntity(@PathVariable Long id) {
        appConfigurationService.deleteAppConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}
