package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetRespUserDto;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetRespUserDtoMapper {

    PetEntity toEntity(PetRespUserDto dto);

    PetRespUserDto toDto(PetEntity entity);

    PetRespUserDto toDtoByAdminDto(PetRespAdminDto adminDto);
}

