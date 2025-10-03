package com.virtualgame.entites.petImage.dto;

import com.virtualgame.entites.petImage.PetImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetImageRespAdminDtoMapper {

    PetImage toEntity(PetImageRespAdminDto dto);

    PetImageRespAdminDto toDto(PetImage entity);

}

