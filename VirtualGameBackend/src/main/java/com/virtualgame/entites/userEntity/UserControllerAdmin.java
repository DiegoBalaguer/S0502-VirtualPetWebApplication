package com.virtualgame.entites.userEntity;

import com.virtualgame.entites.userEntity.dto.UserListAdminDto;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import com.virtualgame.entites.userEntity.dto.UserUpdatePasswordDto;
import com.virtualgame.security.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/user")
//@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "API Manage Users (ADMIN)", description = "API with generic methods for admin user entities.")
public class UserControllerAdmin {

    private final UserServiceImpl userServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Get a complete profile userEntity by his id", description = "Recover a complete profile user by his ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/find/{id}")
    public ResponseEntity<UserRespAdminDto> findUserEntityById(
            @Parameter(
                    name = "id",
                    description = "ID of the user to retrieve",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            example = "2",
                            minimum = "1",
                            description = "User identifier must be a positive integer"
                    )
            )
            @PathVariable Long id) {
        UserRespAdminDto userResp = userServiceImpl.findUserEntityById(id);
        log.debug("usuario: {}", userResp);
        return ResponseEntity.ok(userResp);
    }

    @Operation(summary = "Get a complete profile userEntity by his id", description = "Recover a complete profile user by his ID.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/list")
    public ResponseEntity<List<UserListAdminDto>> findUserEntityList() {
        List<UserListAdminDto> userList = userServiceImpl.findAllUserEntities();
        return ResponseEntity.ok(userList);
    }

    @Operation(summary = "Update user by ID", description = "Update user by by ID.")
    @ApiResponse(responseCode = "204", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUserEntityById(
            @Parameter(
                    name = "id",
                    description = "ID of the user to update",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            example = "2",
                            minimum = "1",
                            description = "User identifier must be a positive integer"
                    )
            )
            @PathVariable Long id, @Valid @RequestBody UserRespAdminDto userRespAdminDto) {

        userServiceImpl.updateUserEntityById(id, currentUserService.getCurrentUserId(), userRespAdminDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Soft delete a user by ID", description = "Soft delete a user by ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> deleteSoftUserEntityById(
            @Parameter(
                    name = "id",
                    description = "ID of the user to delete",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            example = "2",
                            minimum = "1",
                            description = "User identifier must be a positive integer"
                    )
            )
            @PathVariable Long id) {

        userServiceImpl.deleteSoftUserEntityById(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a player by ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserEntityById(
            @Parameter(
                    name = "id",
                    description = "ID of the user to delete",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            example = "2",
                            minimum = "1",
                            description = "User identifier must be a positive integer"
                    )
            )
            @PathVariable Long id) {

        userServiceImpl.deleteUserEntityById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Api for change password of userEntity for Admin.", description = "Change password by userEntity for Admin.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updatePassword(
            @Parameter(
                    name = "id",
                    description = "ID of the user to change password.",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(
                            type = "integer",
                            format = "int64",
                            example = "2",
                            minimum = "1",
                            description = "User identifier must be a positive integer"
                    )
            )
            @PathVariable Long id, @Valid @RequestBody UserUpdatePasswordDto dto) {
        Long userIdAuth = currentUserService.getCurrentUserId();
        userServiceImpl.updatePassword(id, userIdAuth, dto);
        return ResponseEntity.noContent().build();
    }
}
