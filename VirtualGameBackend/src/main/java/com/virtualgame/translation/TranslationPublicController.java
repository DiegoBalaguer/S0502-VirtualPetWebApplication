package com.virtualgame.translation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/translations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "API Manage Translations - Public Translations", description = "Public API for retrieving translations")
public class TranslationPublicController {

    private final TranslationManagerService translationManagerService;

    @GetMapping("/{languageCode}/{messageKey}")
    @Operation(summary = "Get translation",
            description = "Returns the translated text for a specific language and message key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Translation found"),
            @ApiResponse(responseCode = "404", description = "Translation not found")
    })
    public ResponseEntity<String> getTranslation(
            @Parameter(description = "Language code (e.g., en, es, fr)", example = "es")
            @PathVariable String languageCode,
            @Parameter(description = "Message key", example = "welcome.message")
            @PathVariable String messageKey) {
        log.debug("GET /api/public/translations/{}/{} - Getting translation", languageCode, messageKey);
        try {
            String translation = translationManagerService.getTranslation(languageCode, messageKey);
            return ResponseEntity.ok(translation);
        } catch (RuntimeException e) {
            log.warn("Translation not found for language: {}, message: {}", languageCode, messageKey);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{languageCode}/{messageKey}/default")
    @Operation(summary = "Get translation with default",
            description = "Returns the translated text or a default value if not found")
    @ApiResponse(responseCode = "200", description = "Translation or default value returned")
    public ResponseEntity<String> getTranslationWithDefault(
            @Parameter(description = "Language code (e.g., en, es, fr)", example = "es")
            @PathVariable String languageCode,
            @Parameter(description = "Message key", example = "welcome.message")
            @PathVariable String messageKey,
            @Parameter(description = "Default value to return if translation not found", example = "Welcome")
            @RequestParam String defaultValue) {
        log.debug("GET /api/public/translations/{}/{}/default - Getting translation with default",
                languageCode, messageKey);
        String translation = translationManagerService.getTranslation(languageCode, messageKey, defaultValue);
        return ResponseEntity.ok(translation);
    }
}