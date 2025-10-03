package com.virtualgame.entites.petImage.dto;

import com.virtualgame.entites.petImage.PetImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetImageRespUserDtoMapper {

    PetImage toEntity(PetImageRespUserDto dto);

    PetImageRespUserDto toDto(PetImage entity);

    PetImageRespUserDto toDtoByAdminDto(PetImageRespAdminDto adminDto);
}

