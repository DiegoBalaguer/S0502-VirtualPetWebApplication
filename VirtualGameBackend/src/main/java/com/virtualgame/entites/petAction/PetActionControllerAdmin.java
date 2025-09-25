package com.virtualgame.entites.petAction;

import com.virtualgame.entites.petAction.dto.PetActionCreateDto;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petAction.dto.PetActionUpdateDto;
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
@RequestMapping("/api/admin/action")
@Tag(name = "API Manage PetAction (ADMIN)", description = "Endpoints for managing petAction")
public class PetActionControllerAdmin {

    private final PetActionServiceImpl petActionServiceImpl;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Create a new petAction", description = "Creates a new petAction in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "petAction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<PetActionRespAdminDto> createPetAction(
            @RequestBody @Valid PetActionCreateDto createDto) {

        PetActionRespAdminDto createdPetAction = petActionServiceImpl.createPetAction(createDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetAction);
    }

    @Operation(summary = "Find petAction by ID", description = "Retrieves a specific petAction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petAction found"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetActionRespAdminDto> findPetActionById(@PathVariable Long id) {
        PetActionRespAdminDto petAction = petActionServiceImpl.findPetActionById(id);
        return ResponseEntity.ok(petAction);
    }

    @Operation(summary = "Find all petAction", description = "Retrieves all petAction from the system")
    @ApiResponse(responseCode = "200", description = "List of petAction retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetActionRespAdminDto>> findAllPetAction() {
        List<PetActionRespAdminDto> petActions = petActionServiceImpl.findAllPetAction();
        return ResponseEntity.ok(petActions);
    }

    @Operation(summary = "Update petAction", description = "Updates an existing petAction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petAction updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PetActionRespAdminDto> updatePetAction(
            @PathVariable Long id,
            @RequestBody @Valid PetActionUpdateDto updateDto) {
        
        PetActionRespAdminDto updatedPetAction = petActionServiceImpl.updatePetAction(id, updateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.ok(updatedPetAction);
    }

    @Operation(summary = "Soft delete petAction", description = "Marks a petAction as deleted (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petAction soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<Void> deleteSoftPetAction(
            @PathVariable Long id) {

        petActionServiceImpl.deleteSoftPetAction(id, currentUserService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete petAction", description = "Permanently deletes a petAction from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "petAction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePetAction(@PathVariable Long id) {
        petActionServiceImpl.deletePetAction(id);
        return ResponseEntity.noContent().build();
    }
}
