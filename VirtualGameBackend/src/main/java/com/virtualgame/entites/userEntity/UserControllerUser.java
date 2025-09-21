package com.virtualgame.entites.userEntity;

import com.virtualgame.entites.userEntity.dto.UserRespUserDto;
import com.virtualgame.entites.userEntity.mapper.UserRespUserDtoMapper;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import com.virtualgame.entites.userEntity.dto.UserUpdatePasswordDto;
import com.virtualgame.security.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "API Manage Users (USER)", description = "API with generic methods for admin user entities.")
public class UserControllerUser {

    private final UserServiceImpl userServiceImpl;
    private final CurrentUserService currentUserService;
    private final UserRespUserDtoMapper userRespUserDtoMapper;

    @Operation(summary = "Get a basic profile userEntity by his user logged.", description = "Recover a basic profile user by user logged.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/user/find")
    public ResponseEntity<UserRespUserDto> findUserProfile() {
        return ResponseEntity.ok(
                userRespUserDtoMapper.toDtoByAdminDto(
                userServiceImpl.findUserEntityById(currentUserService.getCurrentUserId())));
    }

    @Operation(summary = "Update user by ID", description = "Update user by by ID.")
    @ApiResponse(responseCode = "204", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/user/update")
    public ResponseEntity<Void> updateUserProfile(UserRespAdminDto userRespAdminDto) {
        Long userId = currentUserService.getCurrentUserId();
        userServiceImpl.updateUserEntityById(userId, userId, userRespAdminDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Api for change password of user logged.", description = "Change password by user logged.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PatchMapping("/user/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordDto dto) {
        Long userId = currentUserService.getCurrentUserId();
        userServiceImpl.updatePassword(userId, userId, dto);
        return ResponseEntity.noContent().build();
    }
}
