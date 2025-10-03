package com.virtualgame.entites.petUser;

import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.mapper.PetUserRequestUserDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRespTaskUserDtoMapper;
import com.virtualgame.entites.petUser.tasks.DoAction;
import com.virtualgame.entites.petUser.tasks.DoMove;
import com.virtualgame.security.user.auth.CurrentUserService;
import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/petsusers")
@Tag(name = "API Manage Pets User (USER)", description = "Endpoints for managing user pets")
public class PetUserControllerUser {

    private final PetUserServiceImpl petUserServiceImpl;
    private final DoAction doAction;
    private final DoMove doMove;
    private final CurrentUserService currentUserService;
    private final PetUserRequestUserDtoMapper petUserRequestUserDtoMapper;
    private final PetUserRespTaskUserDtoMapper petUserRespTaskUserDtoMapper;

    @Operation(summary = "Create a new user pet", description = "Creates a new pet in the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PetUserRespTaskUserDto> create(
            @RequestBody @Valid PetUserCreateDto createDto) {
        PetUserRespTaskAdminDto createdPet = petUserServiceImpl.createPetUser(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(petUserRespTaskUserDtoMapper.toDtoByFullAdminDto(createdPet));
    }

    @Operation(summary = "Get pet by ID", description = "Retrieves a specific pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetUserRespTaskUserDto> findPetById(@PathVariable Long id) {
        PetUserRespTaskAdminDto respAdminDto = petUserServiceImpl.findPetUserById(id);
        return ResponseEntity.ok(petUserRespTaskUserDtoMapper.toDtoByFullAdminDto(respAdminDto));
    }

    @Operation(summary = "Get all pets", description = "Retrieves all petsUser by user from the system")
    @ApiResponse(responseCode = "200", description = "List of pets retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetUserRespTaskUserDto>> findPetsByUserId() {
        Long userId = currentUserService.getCurrentUserId();
        log.debug("####################################################");
        log.debug("userId for currentUserService: {}", userId);
        List<PetUserRespTaskAdminDto> respAdminDto = petUserServiceImpl.findPetsUserByUserId(userId);
        return ResponseEntity.ok(respAdminDto.stream()
                .map(petUserRespTaskUserDtoMapper::toDtoByFullAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Update pet", description = "Updates an existing pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetUserRespTaskUserDto> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetUserRequestUserDto userDto) {

        PetUserRespTaskAdminDto respAdminDto =
                petUserServiceImpl
                        .updatePetUser(id,
                                petUserRequestUserDtoMapper.toAdminDtoByDto(userDto),
                                currentUserService.getCurrentUserId());

        return ResponseEntity.ok(petUserRespTaskUserDtoMapper.toDtoByFullAdminDto(respAdminDto));
    }

    @Operation(summary = "Soft delete pet", description = "Marks a pet as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePet(
            @PathVariable Long id) {
        AuthSecurityUserDto authSecurityUserDto = currentUserService.getCurrentUserDto();
        petUserServiceImpl.softDeletePetUserByUserId(authSecurityUserDto, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete petUser", description = "Permanently delete a petUser from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petUserServiceImpl.hardDeletePetUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search pets by type", description = "Searches pets by type ID")
    @ApiResponse(responseCode = "200", description = "List of matching pets")
    @GetMapping("/find-by-type")
    public ResponseEntity<List<PetUserRespTaskUserDto>> findPetsByType(
            @RequestParam Long typeId) {

        List<PetUserRespTaskAdminDto> respAdminDto = petUserServiceImpl.findPetsUserByType(typeId);
        return ResponseEntity.ok(respAdminDto.stream()
                .map(petUserRespTaskUserDtoMapper::toDtoByFullAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Do action for pet user", description = "do action for pet user")
    @ApiResponse(responseCode = "200", description = "do action")
    @PostMapping("/action")
    public ResponseEntity<PetUserRespTaskUserDto> doAction(
            @RequestBody @Valid PetUserTaskRequestUserDto taskDto) {
        PetUserRespTaskAdminDto respAdminDto = doAction
                .doActionPetUser(taskDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(petUserRespTaskUserDtoMapper.toDtoByFullAdminDto(respAdminDto));
    }

    @Operation(summary = "Change habitat for pet user", description = "Change habitat for pet user")
    @ApiResponse(responseCode = "200", description = "Change habitat")
    @PostMapping("/move")
    public ResponseEntity<PetUserRespTaskUserDto> doMove(
            @RequestBody @Valid PetUserTaskRequestUserDto taskDto) {
        PetUserRespTaskAdminDto respAdminDto = doMove
                .doMovePetUser(taskDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(petUserRespTaskUserDtoMapper.toDtoByFullAdminDto(respAdminDto));
    }
}
