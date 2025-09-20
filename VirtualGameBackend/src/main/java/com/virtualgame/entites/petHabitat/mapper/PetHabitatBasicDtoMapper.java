package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatBasicDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatFullDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetHabitatBasicDtoMapper {

    PetHabitat toEntity(PetHabitatBasicDto dto);

    PetHabitatBasicDto toDto(PetHabitat entity);

    PetHabitatBasicDto toDtoByFullDto(PetHabitatFullDto fullDto);
}

