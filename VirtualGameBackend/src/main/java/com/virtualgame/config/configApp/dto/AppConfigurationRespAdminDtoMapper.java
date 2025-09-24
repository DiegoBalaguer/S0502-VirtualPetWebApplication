package com.virtualgame.config.configApp.dto;

import com.virtualgame.config.configApp.AppConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppConfigurationRespAdminDtoMapper {

    AppConfigurationRespAdminDto toDto(AppConfiguration entity);

    AppConfiguration toEntity(AppConfigurationRespAdminDto dto);

    void updateEntityFromDto(AppConfigurationRespAdminDto dto, @MappingTarget AppConfiguration entity);
}