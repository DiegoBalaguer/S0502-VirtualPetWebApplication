package com.virtualgame.entites.petImage;

import com.virtualgame.entites.petImage.dto.PetImageRequestDto;
import com.virtualgame.entites.petImage.dto.PetImageRespAdminDto;
import com.virtualgame.entites.petImage.dto.PetImageRespUserDto;
import com.virtualgame.entites.petImage.dto.PetImageRespUserDtoMapper;
import com.virtualgame.security.user.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/image")
@Tag(name = "API Manage PetImage (USER)", description = "Endpoints for managing PetImage")
public class PetImageControllerUser {

    private final PetImageServiceImpl petImageService;
    private final CurrentUserService currentUserService;
    private final PetImageRespUserDtoMapper petImageRespUserDtoMapper;

    @Operation(summary = "Api for change password of user logged.", description = "Change password by user logged.")
    @ApiResponse(responseCode = "204", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/")
    public ResponseEntity<PetImageRespUserDto> findImage(@Valid @RequestBody PetImageRequestDto dto) {
       PetImageRespAdminDto adminDto = petImageService.findPetImageUrlForAgeByIdAndAge(dto);
        return ResponseEntity.ok(petImageRespUserDtoMapper.toDtoByAdminDto(adminDto));
    }
}
