package com.virtualgame.entites.petEntity;

import com.virtualgame.entites.petEntity.dto.PetCreateDto;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import com.virtualgame.entites.petEntity.dto.PetUpdateDto;
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
@Tag(name = "API Manage Pet Entity (ADMIN)", description = "Endpoints for managing pet entity")
public class PetControllerAdmin {

    private final PetServiceImpl petServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new pet entity", description = "Creates a new pet entity in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pet entity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/pet-entity/create")
    public ResponseEntity<PetRespAdminDto> createPetEntity(
            @RequestBody @Valid PetCreateDto createDto) {

        PetRespAdminDto createdPetEntity = petServiceImpl.createPetEntity(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetEntity);
    }

    @Operation(summary = "Find pet entity by ID", description = "Retrieves a specific pet entity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet entity found"),
            @ApiResponse(responseCode = "404", description = "pet entity not found")
    })
    @GetMapping("/pet-entity/find/{id}")
    public ResponseEntity<PetRespAdminDto> findPetEntityById(@PathVariable Long id) {
        PetRespAdminDto petEntity = petServiceImpl.findPetEntityById(id);
        return ResponseEntity.ok(petEntity);
    }

    @Operation(summary = "Find all pet entity", description = "Retrieves all pet entity from the system")
    @ApiResponse(responseCode = "200", description = "List of pet entity retrieved")
    @GetMapping("/pet-entity/list")
    public ResponseEntity<List<PetRespAdminDto>> findAllPetEntities() {
        List<PetRespAdminDto> petEntitys = petServiceImpl.findAllPetEntity();
        return ResponseEntity.ok(petEntitys);
    }

    @Operation(summary = "Update pet entity", description = "Updates an existing pet entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet entity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "pet entity not found")
    })
    @PutMapping("/pet-entity/update/{id}")
    public ResponseEntity<PetRespAdminDto> updatePetEntity(
            @PathVariable Long id,
            @RequestBody @Valid PetUpdateDto updateDto) {
        
        PetRespAdminDto updatedPetEntity = petServiceImpl.updatePetEntity(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetEntity);
    }

    @Operation(summary = "Soft delete pet entity", description = "Marks a pet entity as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "pet entity soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "pet entity not found")
    })

    @PatchMapping("/pet-entity/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePetEntity(
            @PathVariable Long id) {

        petServiceImpl.softDeletePetEntity(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete pet entity", description = "Permanently deletes a pet entity from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "pet entity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "pet entity not found")
    })
    @DeleteMapping("/pet-entity/delete/{id}")
    public ResponseEntity<Void> hardDeletePetEntity(@PathVariable Long id) {
        petServiceImpl.deletePetEntity(id);
        return ResponseEntity.noContent().build();
    }
}
