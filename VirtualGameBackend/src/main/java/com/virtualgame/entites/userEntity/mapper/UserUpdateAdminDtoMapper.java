package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import com.virtualgame.entites.userEntity.dto.UserUpdateAdminDto;
import com.virtualgame.entites.userEntity.dto.UserUpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateAdminDtoMapper {

    void forUpdateEntityFromDto(UserUpdateAdminDto dto, @MappingTarget UserEntity entity);

    UserEntity toEntity(UserUpdateAdminDto dto);

    UserUpdateAdminDto toDto(UserEntity entity);

    UserUpdateAdminDto  toDtoFromUpdateUserDto(UserUpdateUserDto updateUserDto);
}

