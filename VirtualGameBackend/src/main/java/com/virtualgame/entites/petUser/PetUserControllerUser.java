package com.virtualgame.entites.petUser;

import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.mapper.PetUserRespUserDtoMapper;
import com.virtualgame.security.auth.CurrentUserService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/petsusers")
@Tag(name = "API Manage Pets User (USER)", description = "Endpoints for managing user pets")
public class PetUserControllerUser {

    private final PetUserServiceImpl petUserServiceImpl;
    private final CurrentUserService currentUserService;
    private final PetUserRespUserDtoMapper petUserRespUserDtoMapper;

    @Operation(summary = "Create a new user pet", description = "Creates a new pet in the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PetUserRespAdminDto> create(
            @RequestBody @Valid PetUserCreateDto createDto) {
        PetUserRespAdminDto createdPet = petUserServiceImpl.createPetUser(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @Operation(summary = "Get pet by ID", description = "Retrieves a specific pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetUserRespUserDto> findPetById(@PathVariable Long id) {
        PetUserRespAdminDto respAdminDto = petUserServiceImpl.findPetUserById(id);
        return ResponseEntity.ok(petUserRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }

    @Operation(summary = "Get all pets", description = "Retrieves all pets from the system")
    @ApiResponse(responseCode = "200", description = "List of pets retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetUserRespUserDto>> findPetsByUserId(@PathVariable Long userId) {
        Long userIdnew = currentUserService.getCurrentUserId();
        List<PetUserRespAdminDto> respAdminDto = petUserServiceImpl.findPetsUserByUserId(userIdnew);
        return ResponseEntity.ok(respAdminDto.stream()
                .map(petUserRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Update pet", description = "Updates an existing pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetUserRespUserDto> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetUserRespUserDto userDto) {

        PetUserRespAdminDto respAdminDto =
                petUserServiceImpl
                        .updatePetUser(id,
                                petUserRespUserDtoMapper.toAdminDtoByDto(userDto),
                                currentUserService.getCurrentUserId());

        return ResponseEntity.ok(petUserRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }

    @Operation(summary = "Soft delete pet", description = "Marks a pet as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePet(
            @PathVariable Long id) {

        petUserServiceImpl.softDeletePetUserByUserId(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete pet", description = "Permanently deletes a pet from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petUserServiceImpl.hardDeletePetUserByUserId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search pets by type", description = "Searches pets by type ID")
    @ApiResponse(responseCode = "200", description = "List of matching pets")
    @GetMapping("/find-by-type")
    public ResponseEntity<List<PetUserRespUserDto>> findPetsByType(
            @RequestParam Long typeId) {

        List<PetUserRespAdminDto> respAdminDto = petUserServiceImpl.findPetsUserByType(typeId);
        return ResponseEntity.ok(respAdminDto.stream()
                .map(petUserRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Do action for pet user", description = "do action for pet user")
    @ApiResponse(responseCode = "200", description = "do action")
    @PostMapping("/do-action/{petUserId}/{petUserActionId}")
    public ResponseEntity<PetUserRespUserDto> doAction(
            @PathVariable Long petUserId,
            @PathVariable Long petUserActionId) {

        PetUserRespAdminDto respAdminDto = petUserServiceImpl
                .doActionPetUser(petUserId, currentUserService.getCurrentUserId(), petUserActionId);
        return ResponseEntity.ok(petUserRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }

    @Operation(summary = "Change habitat for pet user", description = "Change habitat for pet user")
    @ApiResponse(responseCode = "200", description = "Change habitat")
    @PostMapping("/do-move/{petUserId}/{petUserHabitatId}")
    public ResponseEntity<PetUserRespUserDto> doMove(
    @PathVariable Long petUserId,
    @PathVariable Long petUserHabitatId) {

        PetUserRespAdminDto respAdminDto = petUserServiceImpl
                .doMovePetUser(petUserId, currentUserService.getCurrentUserId(), petUserHabitatId);
        return ResponseEntity.ok(petUserRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }
}
