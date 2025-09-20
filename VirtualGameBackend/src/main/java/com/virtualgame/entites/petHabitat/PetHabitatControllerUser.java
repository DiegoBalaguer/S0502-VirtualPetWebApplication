package com.virtualgame.entites.petHabitat;

import com.virtualgame.entites.petHabitat.dto.PetHabitatBasicDto;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatBasicDtoMapper;
import com.virtualgame.entites.petHabitat.dto.PetHabitatFullDto;
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
@RequestMapping("/api/user")
@Tag(name = "API Manage Pet Habitat (USER)", description = "Endpoints for managing pet habitat")
public class PetHabitatControllerUser {

    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final PetHabitatBasicDtoMapper petHabitatBasicDtoMapper;

    @Operation(summary = "Find pet habitat by ID", description = "Retrieves a specific pet habitat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet habitat found"),
            @ApiResponse(responseCode = "404", description = "pet habitat not found")
    })
    @GetMapping("/pet-habitat/find/{id}")
    public ResponseEntity<PetHabitatBasicDto> findPetHabitatById(@PathVariable Long id) {
        PetHabitatFullDto petHabitat = petHabitatServiceImpl.findPetHabitatById(id);
        return ResponseEntity.ok(petHabitatBasicDtoMapper.toDtoByFullDto(petHabitat));
    }

    @Operation(summary = "Find all pet habitat", description = "Retrieves all pet habitat from the system")
    @ApiResponse(responseCode = "200", description = "List of pet habitat retrieved")
    @GetMapping("/pet-habitat/list")
    public ResponseEntity<List<PetHabitatBasicDto>> findAllPetEntities() {
        List<PetHabitatFullDto> petEntities = petHabitatServiceImpl.findAllPetHabitat();

        return ResponseEntity.ok(petEntities.stream()
                .map(petHabitatBasicDtoMapper::toDtoByFullDto)
                .collect(Collectors.toList()));
    }
}
