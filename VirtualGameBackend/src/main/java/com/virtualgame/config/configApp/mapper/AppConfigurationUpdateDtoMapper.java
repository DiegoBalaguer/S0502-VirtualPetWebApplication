package com.virtualgame.config.configApp.mapper;

import com.virtualgame.config.configApp.AppConfiguration;
import com.virtualgame.config.configApp.dto.AppConfigurationUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppConfigurationUpdateDtoMapper {

    void forUpdateEntityFromDto(AppConfigurationUpdateDto dto, @MappingTarget AppConfiguration entity);

    AppConfiguration toUpdateEntity(AppConfigurationUpdateDto dto);

    AppConfigurationUpdateDto toUpdateAdminDto(AppConfiguration entity);
}

