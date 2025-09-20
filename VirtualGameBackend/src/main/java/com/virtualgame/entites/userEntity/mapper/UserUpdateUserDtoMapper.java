package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserUpdateUserDto;
import com.virtualgame.entites.userEntity.dto.UserUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateUserDtoMapper {

    UserEntity toEntity(UserUpdateUserDto dto);

    UserUpdateUserDto toBasicDto(UserEntity entity);

    UserUpdateUserDto toBasicFromFullDto(UserUpdateAdminDto dto);
}