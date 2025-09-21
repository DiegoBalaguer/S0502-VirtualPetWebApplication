package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetRespAdminDtoMapper {

    PetRespAdminDto toDto(PetEntity petEntity);

    PetEntity toEntity(PetRespAdminDto petRespAdminDto);

    void updateEntityFromDto(PetRespAdminDto dto, @MappingTarget PetEntity entity);
}