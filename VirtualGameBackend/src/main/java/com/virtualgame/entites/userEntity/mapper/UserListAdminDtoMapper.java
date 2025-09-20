package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserListAdminDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserListAdminDtoMapper {

    UserEntity toEntity(UserListAdminDto dto);

    UserListAdminDto toListDto(UserEntity entity);
}

