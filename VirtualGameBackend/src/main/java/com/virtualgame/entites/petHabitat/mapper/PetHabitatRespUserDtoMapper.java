package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetHabitatRespUserDtoMapper {

    PetHabitat toEntity(PetHabitatRespUserDto dto);

    PetHabitatRespUserDto toDto(PetHabitat entity);

    PetHabitatRespUserDto toDtoByAdminDto(PetHabitatRespAdminDto adminDto);
}

