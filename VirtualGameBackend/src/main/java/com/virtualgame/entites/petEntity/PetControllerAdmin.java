package com.virtualgame.entites.petEntity;

import com.virtualgame.entites.petEntity.dto.PetCreateDto;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import com.virtualgame.entites.petEntity.dto.PetUpdateDto;
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
@RequestMapping("/api/admin/pet")
@Tag(name = "API Manage PetEntity (ADMIN)", description = "Endpoints for managing petEntity")
public class PetControllerAdmin {

    private final PetServiceImpl petServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new petEntity", description = "Creates a new petEntity in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "petEntity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PetRespAdminDto> createPetEntity(
            @RequestBody @Valid PetCreateDto createDto) {

        PetRespAdminDto createdPetEntity = petServiceImpl.createPetEntity(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetEntity);
    }

    @Operation(summary = "Find petEntity by ID", description = "Retrieves a specific petEntity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petEntity found"),
            @ApiResponse(responseCode = "404", description = "petEntity not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetRespAdminDto> findPetEntityById(@PathVariable Long id) {
        PetRespAdminDto petEntity = petServiceImpl.findPetEntityById(id);
        return ResponseEntity.ok(petEntity);
    }

    @Operation(summary = "Find all petEntity", description = "Retrieves all petEntity from the system")
    @ApiResponse(responseCode = "200", description = "List of petEntity retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetRespAdminDto>> findAllPetEntities() {
        List<PetRespAdminDto> petEntitys = petServiceImpl.findAllPetEntity();
        return ResponseEntity.ok(petEntitys);
    }

    @Operation(summary = "Update petEntity", description = "Updates an existing petEntity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petEntity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "petEntity not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetRespAdminDto> updatePetEntity(
            @PathVariable Long id,
            @RequestBody @Valid PetUpdateDto updateDto) {
        
        PetRespAdminDto updatedPetEntity = petServiceImpl.updatePetEntity(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetEntity);
    }

    @Operation(summary = "Soft delete petEntity", description = "Marks a petEntity as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petEntity soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petEntity not found")
    })

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePetEntity(
            @PathVariable Long id) {

        petServiceImpl.softDeletePetEntity(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete petEntity", description = "Permanently deletes a petEntity from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petEntity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petEntity not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> hardDeletePetEntity(@PathVariable Long id) {
        petServiceImpl.deletePetEntity(id);
        return ResponseEntity.noContent().build();
    }
}
