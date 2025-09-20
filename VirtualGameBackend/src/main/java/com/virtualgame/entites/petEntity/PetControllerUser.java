package com.virtualgame.entites.petEntity;

import com.virtualgame.entites.petEntity.dto.PetBasicDto;
import com.virtualgame.entites.petEntity.mapper.PetBasicDtoMapper;
import com.virtualgame.entites.petEntity.dto.PetFullDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "API Manage Pet Entity (USER)", description = "Endpoints for managing pet entity")
public class PetControllerUser {

    private final PetServiceImpl petServiceImpl;
    private final PetBasicDtoMapper petBasicDtoMapper;

    @Operation(summary = "Find pet entity by ID", description = "Retrieves a specific pet entity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet entity found"),
            @ApiResponse(responseCode = "404", description = "pet entity not found")
    })
    @GetMapping("/pet-entity/find/{id}")
    public ResponseEntity<PetBasicDto> findPetEntityById(@PathVariable Long id) {
        PetFullDto petEntity = petServiceImpl.findPetEntityById(id);
        return ResponseEntity.ok(petBasicDtoMapper.toDtoByFullDto(petEntity));
    }

    @Operation(summary = "Find all pet entity", description = "Retrieves all pet entity from the system")
    @ApiResponse(responseCode = "200", description = "List of pet entity retrieved")
    @GetMapping("/pet-entity/list")
    public ResponseEntity<List<PetBasicDto>> findAllPetEntities() {
        List<PetFullDto> petEntities = petServiceImpl.findAllPetEntity();

        return ResponseEntity.ok(petEntities.stream()
                .map(petBasicDtoMapper::toDtoByFullDto)
                .collect(Collectors.toList()));
    }
}
