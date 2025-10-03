package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespTaskAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespTaskUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserRespTaskUserDtoMapper {

    PetUser toEntity(PetUserRespTaskUserDto dto);

    PetUserRespTaskUserDto toDto(PetUser entity);

    PetUserRespAdminDto toFullAdminDtoByDto(PetUserRespTaskUserDto dto);

    PetUserRespTaskUserDto toDtoByFullAdminDto(PetUserRespTaskAdminDto adminFullDto);
}