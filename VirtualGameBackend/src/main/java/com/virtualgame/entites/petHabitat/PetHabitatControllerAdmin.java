package com.virtualgame.entites.petHabitat;

import com.virtualgame.entites.petHabitat.dto.PetHabitatCreateDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatUpdateDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "API Manage Pet Habitat (ADMIN)", description = "Endpoints for managing pet habitat")
public class PetHabitatControllerAdmin {

    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new pet habitat", description = "Creates a new pet habitat in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pet habitat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/pet-habitat/create")
    public ResponseEntity<PetHabitatRespAdminDto> createPetHabitat(
            @RequestBody @Valid PetHabitatCreateDto createDto) {

        PetHabitatRespAdminDto createdPetHabitat = petHabitatServiceImpl.createPetHabitat(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetHabitat);
    }

    @Operation(summary = "Find pet habitat by ID", description = "Retrieves a specific pet habitat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet habitat found"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })
    @GetMapping("/pet-habitat/find/{id}")
    public ResponseEntity<PetHabitatRespAdminDto> findPetHabitatById(@PathVariable Long id) {
        PetHabitatRespAdminDto petHabitat = petHabitatServiceImpl.findPetHabitatById(id);
        return ResponseEntity.ok(petHabitat);
    }

    @Operation(summary = "Find all pet habitat", description = "Retrieves all pet habitat from the system")
    @ApiResponse(responseCode = "200", description = "List of pet habitat retrieved")
    @GetMapping("/pet-habitat/list")
    public ResponseEntity<List<PetHabitatRespAdminDto>> findAllPetEntities() {
        List<PetHabitatRespAdminDto> petHabitats = petHabitatServiceImpl.findAllPetHabitat();
        return ResponseEntity.ok(petHabitats);
    }

    @Operation(summary = "Update pet habitat", description = "Updates an existing pet habitat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet habitat updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })
    @PutMapping("/pet-habitat/update/{id}")
    public ResponseEntity<PetHabitatRespAdminDto> updatePetHabitat(
            @PathVariable Long id,
            @RequestBody @Valid PetHabitatUpdateDto updateDto) {
        
        PetHabitatRespAdminDto updatedPetHabitat = petHabitatServiceImpl.updatePetHabitat(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetHabitat);
    }

    @Operation(summary = "Soft delete pet habitat", description = "Marks a pet habitat as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "pet habitat soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })

    @PatchMapping("/pet-habitat/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePetHabitat(
            @PathVariable Long id) {

        petHabitatServiceImpl.softDeletePetHabitat(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete pet habitat", description = "Permanently deletes a pet habitat from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "pet habitat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })
    @DeleteMapping("/pet-habitat/delete/{id}")
    public ResponseEntity<Void> hardDeletePetHabitat(@PathVariable Long id) {
        petHabitatServiceImpl.deletePetHabitat(id);
        return ResponseEntity.noContent().build();
    }
}
