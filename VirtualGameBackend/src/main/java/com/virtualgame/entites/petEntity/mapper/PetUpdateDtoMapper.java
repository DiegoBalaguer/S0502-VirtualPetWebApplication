package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUpdateDtoMapper {

    void forUpdateEntityFromDto(PetUpdateDto dto, @MappingTarget PetEntity entity);

    PetEntity toUpdateEntity(PetUpdateDto dto);

    PetUpdateDto toUpdateAdminDto(PetEntity entity);
}

