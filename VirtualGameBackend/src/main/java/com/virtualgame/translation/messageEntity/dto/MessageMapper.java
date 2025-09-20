package com.virtualgame.translation.messageEntity.dto;

import com.virtualgame.translation.messageEntity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "messageKey", target = "messageKey")
    MessageDto toDTO(MessageEntity entity);

    @Mapping(source = "messageKey", target = "messageKey")
    MessageEntity toEntity(MessageDto dto);
}