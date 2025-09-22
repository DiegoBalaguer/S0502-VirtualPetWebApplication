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
@RequestMapping("/api/user")
@Tag(name = "API Manage Pet Action (USER)", description = "Endpoints for managing pet action")
public class PetActionControllerUser {

    private final PetActionServiceImpl petActionServiceImpl;
    private final PetActionRespUserDtoMapper petActionRespUserDtoMapper;

    @Operation(summary = "Find pet action by ID", description = "Retrieves a specific pet action by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pet action found"),
            @ApiResponse(responseCode = "404", description = "pet action not found")
    })
    @GetMapping("/pet-action/find/{id}")
    public ResponseEntity<PetActionRespUserDto> findPetActionById(@PathVariable Long id) {
        PetActionRespAdminDto respAdminDto = petActionServiceImpl.findPetActionById(id);
        return ResponseEntity.ok(petActionRespUserDtoMapper.toDtoByAdminDto(respAdminDto));
    }

    @Operation(summary = "Find all pet action", description = "Retrieves all pet action from the system")
    @ApiResponse(responseCode = "200", description = "List of pet action retrieved")
    @GetMapping("/pet-action/list")
    public ResponseEntity<List<PetActionRespUserDto>> findAllPetEntities() {
        List<PetActionRespAdminDto> respAdminDto = petActionServiceImpl.findAllPetAction();

        return ResponseEntity.ok(respAdminDto.stream()
                .map(petActionRespUserDtoMapper::toDtoByAdminDto)
                .collect(Collectors.toList()));
    }
}
