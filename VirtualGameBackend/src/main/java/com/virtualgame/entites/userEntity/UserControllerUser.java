package com.virtualgame.entites.userEntity;

import com.virtualgame.entites.userEntity.dto.*;
import com.virtualgame.entites.userEntity.mapper.UserRespUserDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserUpdateAdminDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserUpdateUserDtoMapper;
import com.virtualgame.security.user.auth.CurrentUserService;
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
@RequestMapping("/api/user/user")
@RequiredArgsConstructor
@Tag(name = "API Manage Users (USER)", description = "API with generic methods for admin user entities.")
public class UserControllerUser {

    private final UserServiceImpl userServiceImpl;
    private final CurrentUserService currentUserService;
    private final UserRespUserDtoMapper userRespUserDtoMapper;
    private final UserUpdateUserDtoMapper  userUpdateUserDtoMapper;
    private final UserUpdateAdminDtoMapper userUpdateAdminDtoMapper;

    @Operation(summary = "Get a basic profile userEntity by his user logged.", description = "Recover a basic profile user by user logged.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/find")
    public ResponseEntity<UserRespUserDto> findUserProfile() {
        return ResponseEntity.ok(
                userRespUserDtoMapper.toDtoByAdminDto(
                userServiceImpl.findUserEntityById(currentUserService.getCurrentUserId())));
    }

    @Operation(summary = "Update user by ID", description = "Update user by by ID.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/update")
    public ResponseEntity<UserUpdateUserDto> updateUserProfile(@RequestBody @Valid UserUpdateUserDto userUpdateUserDto) {
        Long userId = currentUserService.getCurrentUserId();
        UserUpdateAdminDto userUpdateAdminDto = userUpdateAdminDtoMapper.toDtoFromUpdateUserDto(userUpdateUserDto);
        log.debug("#################################################");
        log.debug("userId: {}", userId);
        log.debug("userDto recibido: {}", userUpdateUserDto);
        log.debug("userDto Admin: {}", userUpdateAdminDto);
        log.debug("###################################################");
        return ResponseEntity.ok(
                userUpdateUserDtoMapper.toDtoFromUpdateAdminDto(
                        userServiceImpl.updateUserEntityById(userId, userId, userUpdateAdminDto)));
    }

    @Operation(summary = "Api for change password of user logged.", description = "Change password by user logged.")
    @ApiResponse(responseCode = "204", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordDto dto) {
        Long userId = currentUserService.getCurrentUserId();
        userServiceImpl.updatePassword(userId, userId, dto);
        return ResponseEntity.noContent().build();
    }
}
