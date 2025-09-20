package com.virtualgame.translation.languageEntity.dto;

import com.virtualgame.translation.languageEntity.LanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LanguageCreateDtoMapper {

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageCreateDto toDTO(LanguageEntity entity);

    @Mapping(source = "nativeName", target = "nativeName")
    LanguageEntity toEntity(LanguageCreateDto dto);
}