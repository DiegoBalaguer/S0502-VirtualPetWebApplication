package com.virtualgame.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-config")
@Getter
@Setter
public class AppProperties {

    // SYSTEM
    private Boolean heartbeatIsActive;
    private String heartbeatAddressHost;
    private String heartbeatEndPoint;
    private String heartbeatTimeInterval;
    private String defaultLanguageSystem;
    private String defaultLanguageUser;

    // ENTITIES
    private String defaultUserEntityImageUrl;
    private String defaultPetActionImageUrl;
    private String defaultPetEntityImageUrl;
    private String defaultPetHabitatImageUrl;
    private String defaultPetUserEntityImageUrl;
    private Integer defaultPetHappy;
    private Integer defaultPetTired;
    private Integer defaultPetHungry;
    private Integer defaultPetHappyReps;
    private Integer defaultPetTiredReps;
    private Integer defaultPetHungryReps;
    private Integer defaultPetHappyDangerReps;
    private Integer defaultPetTiredDangerReps;
    private Integer defaultPetHungryDangerReps;
    private Integer defaultPetHappyDangerMin;
    private Integer defaultPetTiredDangerMin;
    private Integer defaultPetHungryDangerMin;
    private Integer defaultPetHappyDanger01Max;
    private Integer defaultPetHappyDanger02Max;
    private Integer defaultPetTiredDangerMax;
    private Integer defaultPetHungryDangerMax;
    private Integer defaultPetMonths;
    private Integer defaultPetAge;
    private Integer defaultPetActionHappyMax;
    private Integer defaultPetActionAgeMin;
    private Integer defaultPetHabitatDomedCityId;
    private Integer defaultPetHabitatSanctuaryId;
    private Integer defaultPetHabitatEscapeId;





}
