package com.virtualgame.translation.languageEntity.dto;

import com.virtualgame.translation.languageEntity.LanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LanguageUpdateDtoMapper {
    void forUpdateEntityFromDto(LanguageUpdateDto dto, @MappingTarget LanguageEntity entity);

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageUpdateDto toDTO(LanguageEntity entity);

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageEntity toEntity(LanguageUpdateDto dto);

}
