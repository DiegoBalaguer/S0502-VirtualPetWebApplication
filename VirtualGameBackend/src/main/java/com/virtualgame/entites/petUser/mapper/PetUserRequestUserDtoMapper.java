package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRequestUserDto;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserRequestUserDtoMapper {

    PetUser toEntity(PetUserRequestUserDto dto);

    PetUserRequestUserDto toDto(PetUser entity);

    PetUserRespAdminDto toAdminDtoByDto(PetUserRequestUserDto dto);

    PetUserRequestUserDto toDtoByAdminDto(PetUserRespAdminDto adminDto);


}