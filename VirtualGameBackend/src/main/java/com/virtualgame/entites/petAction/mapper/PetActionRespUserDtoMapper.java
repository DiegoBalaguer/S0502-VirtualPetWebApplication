package com.virtualgame.entites.petAction.mapper;

import com.virtualgame.entites.petAction.PetAction;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petAction.dto.PetActionRespUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetActionRespUserDtoMapper {

    PetAction toEntity(PetActionRespUserDto dto);

    PetActionRespUserDto toDto(PetAction entity);

    PetActionRespUserDto toDtoByAdminDto(PetActionRespAdminDto adminDto);
}

