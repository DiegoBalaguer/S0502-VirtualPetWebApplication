package com.virtualgame.entites.petHabitat;

import com.virtualgame.entites.petHabitat.dto.PetHabitatCreateDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatUpdateDto;
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
@RequestMapping("/api/admin/habitat")
@Tag(name = "API Manage PetHabitat (ADMIN)", description = "Endpoints for managing petHabitat")
public class PetHabitatControllerAdmin {

    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new petHabitat", description = "Creates a new petHabitat in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "petHabitat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PetHabitatRespAdminDto> createPetHabitat(
            @RequestBody @Valid PetHabitatCreateDto createDto) {

        PetHabitatRespAdminDto createdPetHabitat = petHabitatServiceImpl.createPetHabitat(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetHabitat);
    }

    @Operation(summary = "Find petHbitat by ID", description = "Retrieves a specific petHabitat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petHabitat found"),
            @ApiResponse(responseCode = "404", description = "petHabitat not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetHabitatRespAdminDto> findPetHabitatById(@PathVariable Long id) {
        PetHabitatRespAdminDto petHabitat = petHabitatServiceImpl.findPetHabitatById(id);
        return ResponseEntity.ok(petHabitat);
    }

    @Operation(summary = "Find all petHabitat", description = "Retrieves all petHabitat from the system")
    @ApiResponse(responseCode = "200", description = "List of petHabitat retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetHabitatRespAdminDto>> findAllPetEntities() {
        List<PetHabitatRespAdminDto> petHabitats = petHabitatServiceImpl.findAllPetHabitat();
        return ResponseEntity.ok(petHabitats);
    }

    @Operation(summary = "Update petHabitat", description = "Updates an existing petHabitat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petHabitat updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "petHabitat not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetHabitatRespAdminDto> updatePetHabitat(
            @PathVariable Long id,
            @RequestBody @Valid PetHabitatUpdateDto updateDto) {
        
        PetHabitatRespAdminDto updatedPetHabitat = petHabitatServiceImpl.updatePetHabitat(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetHabitat);
    }

    @Operation(summary = "Soft delete petHabitat", description = "Marks a petHabitat as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petHabitat soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petHabitat not found")
    })

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> softDeletePetHabitat(
            @PathVariable Long id) {

        petHabitatServiceImpl.softDeletePetHabitat(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete petHabitat", description = "Permanently deletes a petHabitat from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petHabitat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petHabitat not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> hardDeletePetHabitat(@PathVariable Long id) {
        petHabitatServiceImpl.deletePetHabitat(id);
        return ResponseEntity.noContent().build();
    }
}
