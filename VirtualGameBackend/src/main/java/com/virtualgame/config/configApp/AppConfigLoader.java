package com.virtualgame.config.configApp;

import com.virtualgame.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppConfigLoader implements ApplicationRunner {

    private final AppConfigurationService configuracionService;
    private final AppProperties appProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting to load application properties from the database.");

        Map<String, AppConfiguration> configsMap = configuracionService.findAllAppConfigurationAsMap();

        for (Field field : AppProperties.class.getDeclaredFields()) {
            String fieldName = field.getName();
            String fieldNameLowerCase = fieldName.toLowerCase();

            if (configsMap.containsKey(fieldNameLowerCase)) {
                AppConfiguration config = configsMap.get(fieldNameLowerCase);

                field.setAccessible(true);

                Object convertedValue = convertValue(config.getKeyValue(), field.getType());

                if (convertedValue != null) {
                    field.set(appProperties, convertedValue);
                    log.debug("Property '{}' updated from DB with value: {}", fieldName, convertedValue);
                } else {
                    log.warn("Value for property '{}' could not be converted or was null. Using default value.", fieldName);
                }
            } else {
                log.debug("Property '{}' not found in database. Using value from application.properties.", fieldName);
            }
        }
        log.info("Application properties loaded successfully.");
    }

    private <T> T convertValue(String value, Class<T> targetType) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        if (targetType.equals(String.class)) {
            return targetType.cast(value);
        } else if (targetType.equals(Integer.class)) {
            return targetType.cast(Integer.valueOf(value));
        } else if (targetType.equals(Boolean.class)) {
            return targetType.cast(Boolean.valueOf(value));
        } else if (targetType.equals(Double.class)) {
            return targetType.cast(Double.valueOf(value));
        } else if (targetType.equals(Float.class)) {
            return targetType.cast(Float.valueOf(value));
        } else if (targetType.equals(Long.class)) {
            return targetType.cast(Long.valueOf(value));
        } else {
            throw new IllegalArgumentException("Unknown data type for conversion: " + targetType.getSimpleName());
        }
    }
}
