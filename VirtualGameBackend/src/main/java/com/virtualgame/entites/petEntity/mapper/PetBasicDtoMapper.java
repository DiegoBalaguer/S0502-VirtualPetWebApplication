package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetBasicDto;
import com.virtualgame.entites.petEntity.dto.PetFullDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetBasicDtoMapper {

    PetEntity toEntity(PetBasicDto dto);

    PetBasicDto toDto(PetEntity entity);

    PetBasicDto toDtoByFullDto(PetFullDto fullDto);
}

