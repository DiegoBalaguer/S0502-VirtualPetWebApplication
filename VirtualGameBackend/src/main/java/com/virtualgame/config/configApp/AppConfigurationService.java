package com.virtualgame.config.configApp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfigurationService {

    private final AppConfigurationRepository repository;

    public List<AppConfiguration> findAllAppConfiguration() {
        return repository.findAll();
    }

    public Map<String, AppConfiguration> findAllAppConfigurationAsMap() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(
                        config -> config.getKeyName().toLowerCase(),
                        config -> config
                ));
    }
}
