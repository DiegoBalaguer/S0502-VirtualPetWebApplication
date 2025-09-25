package com.virtualgame.entites.petAction;

import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petAction.dto.PetActionRespUserDto;
import com.virtualgame.entites.petAction.mapper.PetActionRespUserDtoMapper;
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
@RequestMapping("/api/user/action")
@Tag(name = "API Manage PetAction (USER)", description = "Endpoints for managing petAction")
public class PetActionControllerUser {

    private final PetActionServiceImpl petActionServiceImpl;
    private final PetActionRespUserDtoMapper petActionRespUserDtoMapper;

    @Operation(summary = "Find petAction by ID", description = "Retrieves a specific petAction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petAction found"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<PetActionRespUserDto> findPetActionById(@PathVariable Long id) {
        PetActionRespAdminDto respAdminDto = petActionServiceImpl.findPetActionById(id);
        return ResponseEntity.ok(petActionRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }

    @Operation(summary = "Find all petAction", description = "Retrieves all petAction from the system")
    @ApiResponse(responseCode = "200", description = "List of petAction retrieved")
    @GetMapping("/list")
    public ResponseEntity<List<PetActionRespUserDto>> findAllPetEntities() {
        List<PetActionRespAdminDto> respAdminDto = petActionServiceImpl.findAllPetAction();

        return ResponseEntity.ok(respAdminDto.stream()
                .map(petActionRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "List petAction by habitatID", description = "Retrieves a list habitats by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "petAction found"),
            @ApiResponse(responseCode = "404", description = "petAction not found")
    })
    @GetMapping("/list/{habitatId}")
    public ResponseEntity<List<PetActionRespUserDto>> findPetActionByHabitatId(@PathVariable Long habitatId) {
        List<PetActionRespAdminDto> respAdminDto = petActionServiceImpl.findPetActionByHabitatId(habitatId);
        return ResponseEntity.ok(respAdminDto.stream()
                .map(petActionRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }
}
