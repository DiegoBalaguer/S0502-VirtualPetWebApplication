package com.virtualgame.translation.messageEntity;

import com.virtualgame.translation.messageEntity.dto.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/translation/messages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API Manage Translations - Messages (ADMIN)", description = "API for managing message keys")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @Operation(summary = "Get all messages", description = "Returns a list of all available message keys")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved messages")
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        log.info("GET /api/messages - Getting all messages");
        List<MessageDto> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get message by ID", description = "Returns a specific message by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message found"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    public ResponseEntity<MessageDto> getMessageById(
            @Parameter(description = "ID of the message to retrieve", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/messages/{} - Getting message by ID", id);
        return messageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/key/{key}")
    @Operation(summary = "Get message by key", description = "Returns a specific message by its key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message found"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    public ResponseEntity<MessageDto> getMessageByKey(
            @Parameter(description = "Key of the message to retrieve", example = "welcome.message")
            @PathVariable String key) {
        log.info("GET /api/messages/key/{} - Getting message by key", key);
        return messageService.findByMessageKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new message", description = "Creates a new message key with the provided data")
    @ApiResponse(responseCode = "201", description = "Message created successfully")
    public ResponseEntity<MessageDto> createMessage(
            @Parameter(description = "Message data to create")
            @Valid @RequestBody MessageDto messageDTO) {
        log.info("POST /api/messages - Creating new message: {}", messageDTO.getMessageKey());
        MessageDto createdMessage = messageService.create(messageDTO);
        return ResponseEntity.status(201).body(createdMessage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a message", description = "Updates an existing message with the provided data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message updated successfully"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    public ResponseEntity<MessageDto> updateMessage(
            @Parameter(description = "ID of the message to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated message data")
            @Valid @RequestBody MessageDto messageDTO) {
        log.info("PUT /api/messages/{} - Updating message", id);
        try {
            MessageDto updatedMessage = messageService.update(id, messageDTO);
            return ResponseEntity.ok(updatedMessage);
        } catch (RuntimeException e) {
            log.warn("Message not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a message", description = "Deletes a message by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Message deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Message not found")
    })
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "ID of the message to delete", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/messages/{} - Deleting message", id);
        try {
            messageService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Message not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}