package com.virtualgame.translation.translationEntity;

import com.virtualgame.translation.translationEntity.dto.TranslationDto;
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
@RequestMapping("/api/admin/translation/translations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API Manage Translations - Translations (ADMIN)", description = "API for managing translations")
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping
    @Operation(summary = "Get all translations", description = "Returns a list of all available translations")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved translations")
    public ResponseEntity<List<TranslationDto>> getAllTranslations() {
        log.info("GET /api/translations - Getting all translations");
        List<TranslationDto> translations = translationService.findAll();
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get translation by ID", description = "Returns a specific translation by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Translation found"),
            @ApiResponse(responseCode = "404", description = "Translation not found")
    })
    public ResponseEntity<TranslationDto> getTranslationById(
            @Parameter(description = "ID of the translation to retrieve", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/translations/{} - Getting translation by ID", id);
        return translationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/language/{languageCode}/message/{messageKey}")
    @Operation(summary = "Get translation by language and message",
            description = "Returns a translation for specific language code and message key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Translation found"),
            @ApiResponse(responseCode = "404", description = "Translation not found")
    })
    public ResponseEntity<TranslationDto> getTranslationByLanguageAndMessage(
            @Parameter(description = "Language code", example = "es")
            @PathVariable String languageCode,
            @Parameter(description = "Message key", example = "welcome.message")
            @PathVariable String messageKey) {
        log.info("GET /api/translations/language/{}/message/{} - Getting translation", languageCode, messageKey);
        return translationService.findByLanguageCodeAndMessageKey(languageCode, messageKey)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new translation", description = "Creates a new translation with the provided data")
    @ApiResponse(responseCode = "201", description = "Translation created successfully")
    public ResponseEntity<TranslationDto> createTranslation(
            @Parameter(description = "Translation data to create")
            @Valid @RequestBody TranslationDto translationDTO) {
        log.info("POST /api/translations - Creating new translation");
        TranslationDto createdTranslation = translationService.create(translationDTO);
        return ResponseEntity.status(201).body(createdTranslation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a translation", description = "Updates an existing translation with the provided data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Translation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Translation not found")
    })
    public ResponseEntity<TranslationDto> updateTranslation(
            @Parameter(description = "ID of the translation to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated translation data")
            @Valid @RequestBody TranslationDto translationDTO) {
        log.info("PUT /api/translations/{} - Updating translation", id);
        try {
            TranslationDto updatedTranslation = translationService.update(id, translationDTO);
            return ResponseEntity.ok(updatedTranslation);
        } catch (RuntimeException e) {
            log.warn("Translation not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a translation", description = "Deletes a translation by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Translation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Translation not found")
    })
    public ResponseEntity<Void> deleteTranslation(
            @Parameter(description = "ID of the translation to delete", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/translations/{} - Deleting translation", id);
        try {
            translationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Translation not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}