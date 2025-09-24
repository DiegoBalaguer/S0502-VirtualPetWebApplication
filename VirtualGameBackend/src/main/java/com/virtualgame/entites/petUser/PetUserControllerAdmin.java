package com.virtualgame.entites.petUser;

import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.security.auth.CurrentUserService;
import com.virtualgame.entites.petUser.dto.PetUserCreateDto;
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
@RequestMapping("/api/admin/petsuser")
@Tag(name = "API Manage Pets User (ADMIN)", description = "Endpoints for managing user pets")
public class PetUserControllerAdmin {

    private final PetUserServiceImpl petUserServiceImpl;
    private final CurrentUserService currentUserService;

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
    public ResponseEntity<PetUserRespAdminDto> findPetById(@PathVariable Long id) {
        PetUserRespAdminDto pet = petUserServiceImpl.findPetUserById(id);
        return ResponseEntity.ok(pet);
    }

    @Operation(summary = "Get all pets", description = "Retrieves all pets from the system")
    @ApiResponse(responseCode = "200", description = "List of pets retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetUserRespAdminDto>> findAllPets() {
        List<PetUserRespAdminDto> pets = petUserServiceImpl.findAllPetsUser();
        return ResponseEntity.ok(pets);
    }

    @Operation(summary = "Update pet", description = "Updates an existing pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetUserRespAdminDto> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetUserRespAdminDto fullDto) {

        PetUserRespAdminDto updatedPet = petUserServiceImpl.updatePetUser(id, fullDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPet);
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
    public ResponseEntity<List<PetUserRespAdminDto>> findPetsByType(
            @RequestParam Long typeId) {

        List<PetUserRespAdminDto> pets = petUserServiceImpl.findPetsUserByType(typeId);
        return ResponseEntity.ok(pets);
    }
}
