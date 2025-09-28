package com.virtualgame.config.configApp.mapper;

import com.virtualgame.config.configApp.AppConfiguration;
import com.virtualgame.config.configApp.dto.AppConfigurationRespAdminDto;
import com.virtualgame.config.configApp.dto.AppConfigurationRespUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppConfigurationRespUserDtoMapper {

    AppConfigurationRespUserDto toDto(AppConfiguration entity);

    AppConfiguration toEntity(AppConfigurationRespUserDto dto);

    AppConfigurationRespUserDto toDtoByAdminDto(AppConfigurationRespAdminDto adminDto);
}