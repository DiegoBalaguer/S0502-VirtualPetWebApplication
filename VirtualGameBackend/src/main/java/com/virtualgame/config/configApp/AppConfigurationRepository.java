package com.virtualgame.config.configApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Long> {
    Boolean existsByKeyName(String keyName);
}
