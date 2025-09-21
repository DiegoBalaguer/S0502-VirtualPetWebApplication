package com.virtualgame.translation.languageEntity;

import com.virtualgame.security.auth.CurrentUserService;
import com.virtualgame.translation.languageEntity.dto.LanguageCreateDto;
import com.virtualgame.translation.languageEntity.dto.LanguageDto;
import com.virtualgame.translation.languageEntity.dto.LanguageUpdateDto;
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
@RequestMapping("/api/admin/translation/languages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API Manage Translations - Languages (ADMIN)", description = "API for managing languages")
public class LanguageControllerAdmin {

    private final LanguageServiceImpl languageServiceImpl;
    private final CurrentUserService currentUserService;

    @GetMapping
    @Operation(summary = "Get all languages", description = "Returns a list of all available languages")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved languages")
    public ResponseEntity<List<LanguageDto>> getAllLanguages() {
        log.info("GET /api/languages - Getting all languages");
        List<LanguageDto> languages = languageServiceImpl.findAllLanguage();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get language by ID", description = "Returns a specific language by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Language found"),
            @ApiResponse(responseCode = "404", description = "Language not found")
    })
    public ResponseEntity<LanguageDto> getLanguageById(
            @Parameter(description = "ID of the language to retrieve", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/languages/{} - Getting language by ID", id);
        return ResponseEntity.ok(languageServiceImpl.findLanguageById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get language by code", description = "Returns a specific language by its code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Language found"),
            @ApiResponse(responseCode = "404", description = "Language not found")
    })
    public ResponseEntity<LanguageDto> getLanguageByCode(
            @Parameter(description = "Code of the language to retrieve", example = "es")
            @PathVariable String code) {
        log.info("GET /api/languages/code/{} - Getting language by code", code);
        return languageServiceImpl.findLanguageByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new language", description = "Creates a new language with the provided data")
    @ApiResponse(responseCode = "201", description = "Language created successfully")
    public ResponseEntity<LanguageDto> createLanguage(
            @Parameter(description = "Language data to create")
            @Valid @RequestBody LanguageCreateDto languageCreateDto) {
        log.info("POST /api/languages - Creating new language: {}", languageCreateDto.code());
        LanguageDto createdLanguage = languageServiceImpl.createLanguage(languageCreateDto, currentUserService.getCurrentUserId());
        return ResponseEntity.status(201).body(createdLanguage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a language", description = "Updates an existing language with the provided data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Language updated successfully"),
            @ApiResponse(responseCode = "404", description = "Language not found")
    })
    public ResponseEntity<LanguageDto> updateLanguage(
            @Parameter(description = "ID of the language to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated language data")
            @Valid @RequestBody LanguageUpdateDto languageUpdateDTO) {
        log.info("PUT /api/languages/{} - Updating language", id);
        try {
            LanguageDto updatedLanguage = languageServiceImpl.updateLanguage(id, languageUpdateDTO, currentUserService.getCurrentUserId());
            return ResponseEntity.ok(updatedLanguage);
        } catch (RuntimeException e) {
            log.warn("Language not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a language", description = "Deletes a language by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Language deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Language not found")
    })
    public ResponseEntity<Void> deleteLanguage(
            @Parameter(description = "ID of the language to delete", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/languages/{} - Deleting language", id);
        try {
            languageServiceImpl.deleteLanguage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Language not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}