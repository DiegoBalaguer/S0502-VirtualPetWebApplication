package com.virtualgame.translation.messageEntity;

import com.virtualgame.translation.TranslationCacheService;
import com.virtualgame.translation.messageEntity.dto.MessageDto;
import com.virtualgame.translation.messageEntity.dto.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final TranslationCacheService translationCacheService;

    public List<MessageDto> findAll() {
        log.info("Finding all messages");
        return messageRepository.findAll().stream()
                .map(MessageMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MessageDto> findById(Long id) {
        log.info("Finding message by id: {}", id);
        return messageRepository.findById(id)
                .map(MessageMapper.INSTANCE::toDTO);
    }

    public Optional<MessageDto> findByMessageKey(String messageKey) {
        log.info("Finding message by key: {}", messageKey);
        return messageRepository.findByMessageKey(messageKey)
                .map(MessageMapper.INSTANCE::toDTO);
    }

    @Transactional
    public MessageDto create(MessageDto messageDTO) {
        log.info("Creating new message: {}", messageDTO.getMessageKey());
        if (messageRepository.existsByMessageKey(messageDTO.getMessageKey())) {
            throw new RuntimeException("Message with key " + messageDTO.getMessageKey() + " already exists");
        }

        MessageEntity entity = MessageMapper.INSTANCE.toEntity(messageDTO);
        MessageEntity saved = messageRepository.save(entity);

        translationCacheService.reloadCache();
        log.info("Message created successfully: {}", saved.getMessageKey());

        return MessageMapper.INSTANCE.toDTO(saved);
    }

    @Transactional
    public MessageDto update(Long id, MessageDto messageDTO) {
        log.info("Updating message with id: {}", id);
        MessageEntity existing = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));

        existing.setDescription(messageDTO.getDescription());

        MessageEntity updated = messageRepository.save(existing);

        translationCacheService.clearCache();
        log.info("Message updated successfully: {}", updated.getMessageKey());

        return MessageMapper.INSTANCE.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting message with id: {}", id);
        MessageEntity entity = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));

        messageRepository.delete(entity);
        translationCacheService.clearCache();
        log.info("Message deleted successfully: {}", entity.getMessageKey());
    }
}