package com.virtualgame.translation.translationEntity.dto;

import com.virtualgame.translation.languageEntity.dto.LanguageDtoMapper;
import com.virtualgame.translation.messageEntity.dto.MessageMapper;
import com.virtualgame.translation.translationEntity.TranslationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LanguageDtoMapper.class, MessageMapper.class})
public interface TranslationMapper {
    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);

    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.code", target = "languageCode")
    @Mapping(source = "message.id", target = "messageId")
    @Mapping(source = "message.messageKey", target = "messageKey")
    @Mapping(source = "translatedText", target = "translatedText")
    TranslationDto toDTO(TranslationEntity entity);

    @Mapping(source = "languageId", target = "language.id")
    @Mapping(source = "messageId", target = "message.id")
    @Mapping(source = "translatedText", target = "translatedText")
    TranslationEntity toEntity(TranslationDto dto);
}