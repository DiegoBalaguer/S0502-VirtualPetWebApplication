package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserRespUserDto;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import com.virtualgame.entites.userEntity.dto.UserUpdateAdminDto;
import com.virtualgame.entites.userEntity.mapper.RoleMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserRespUserDtoMapper {

    UserEntity toEntity(UserRespUserDto dto);

    @Mapping(target = "roleList", source = "entity", qualifiedByName = "mapRoles")
    UserRespUserDto toDto(UserEntity entity);

    UserRespUserDto toDtoByAdminDto(UserRespAdminDto adminDto);

    UserRespUserDto toDtoFromUpdateAdminDto(UserUpdateAdminDto userUpdateAdminDto);
}
