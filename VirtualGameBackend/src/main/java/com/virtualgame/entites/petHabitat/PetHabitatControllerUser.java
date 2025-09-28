package com.virtualgame.entites.petHabitat;

import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespUserDto;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatRespUserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/habitat")
@Tag(name = "API Manage PetHabitat (USER)", description = "Endpoints for managing petHabitat")
public class PetHabitatControllerUser {

    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final PetHabitatRespUserDtoMapper petHabitatRespUserDtoMapper;

    @Operation(summary = "Find petHabitat by ID", description = "Retrieves a specific petHabitat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet habitat found"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetHabitatRespUserDto> findPetHabitatById(@PathVariable Long id) {
        PetHabitatRespAdminDto petHabitat = petHabitatServiceImpl.findPetHabitatById(id);
        return ResponseEntity.ok(petHabitatRespUserDtoMapper.toDtoByAdminDto(petHabitat));
    }

    @Operation(summary = "Find all petHabitat", description = "Retrieves all petHabitat from the system")
    @ApiResponse(responseCode = "200", description = "List of pet habitat retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetHabitatRespUserDto>> findAllPetHabitats() {
        List<PetHabitatRespAdminDto> petEntities = petHabitatServiceImpl.findAllPetHabitat();

        return ResponseEntity.ok(petEntities.stream()
                .map(petHabitatRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Find all petHabitat by parentId", description = "Retrieves all petHabitat by parentId from the system")
    @ApiResponse(responseCode = "200", description = "List of pet habitat retrieved")
    @GetMapping("/list/{habitatId}")
    public ResponseEntity<List<PetHabitatRespUserDto>> findAllPetHabitats(@PathVariable Long habitatId) {
        List<PetHabitatRespAdminDto> petEntities = petHabitatServiceImpl.findAllPetHabitatByParentId(habitatId);

        return ResponseEntity.ok(petEntities.stream()
                .map(petHabitatRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }
}
