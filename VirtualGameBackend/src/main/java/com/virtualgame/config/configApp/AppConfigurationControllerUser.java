package com.virtualgame.config.configApp;

import com.virtualgame.config.configApp.dto.AppConfigurationRespAdminDto;
import com.virtualgame.config.configApp.dto.AppConfigurationRespUserDto;
import com.virtualgame.config.configApp.mapper.AppConfigurationRespUserDtoMapper;
import com.virtualgame.security.user.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/parameters")
@Tag(name = "API Manage parameters application (USER)", description = "Endpoints for managing parameters application")
public class AppConfigurationControllerUser {

    private final AppConfigurationService appConfigurationService;
    private final CurrentUserService currentUserService;
    private final AppConfigurationRespUserDtoMapper appConfigurationRespUserDtoMapper;

    @Operation(summary = "Find all app configuration", description = "Retrieves all app configuration from the system")
    @ApiResponse(responseCode = "200", description = "List of app configuration retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<AppConfigurationRespUserDto>> findAllPetEntities() {
        Long userId = currentUserService.getCurrentUserId();
        List<AppConfigurationRespAdminDto> respAdminDto = appConfigurationService.findAllAppConfiguration();
            return ResponseEntity.ok(respAdminDto.stream()
                    .map(appConfigurationRespUserDtoMapper::toDtoByAdminDto)
                    .collect(Collectors.toList()));



    }
}
