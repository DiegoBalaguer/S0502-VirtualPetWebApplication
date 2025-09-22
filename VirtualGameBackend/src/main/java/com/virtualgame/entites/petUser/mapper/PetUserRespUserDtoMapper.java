package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserRespUserDtoMapper {

    PetUser toEntity(PetUserRespUserDto dto);

    PetUserRespUserDto toDto(PetUser entity);

    PetUserRespAdminDto toAdminDtoByDto(PetUserRespUserDto dto);

    PetUserRespUserDto toDtoByAdminDto(PetUserRespAdminDto adminDto);


}