package com.virtualgame.translation.languageEntity.dto;

import com.virtualgame.translation.languageEntity.LanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LanguageDtoMapper {

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageDto toDto(LanguageEntity entity);

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageEntity toEntity(LanguageDto dto);
}