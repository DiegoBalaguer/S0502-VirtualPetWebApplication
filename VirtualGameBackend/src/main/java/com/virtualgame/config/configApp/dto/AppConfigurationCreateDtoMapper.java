package com.virtualgame.config.configApp.dto;

import com.virtualgame.config.configApp.AppConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppConfigurationCreateDtoMapper {

    AppConfigurationCreateDto toDto(AppConfiguration entity);

    AppConfiguration toEntity(AppConfigurationCreateDto dto);

    void updateEntityFromDto(AppConfigurationCreateDto dto, @MappingTarget AppConfiguration entity);
}