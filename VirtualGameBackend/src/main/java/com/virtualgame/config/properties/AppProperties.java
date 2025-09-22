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

    private Integer defaultPetAgeDieDomedCity;
    private Integer defaultPetAgeDieMinOutside;
    private Integer defaultPetAgeDieMaxOutside;

    private Integer defaultPetMouthsToAge;

    private Integer defaultPetMonths;
    private Integer defaultPetAge;

    private Integer defaultPetHappy;
    private Integer defaultPetTired;
    private Integer defaultPetHungry;

    private Integer defaultPetHappyReps;
    private Integer defaultPetTiredReps;
    private Integer defaultPetHungryReps;

    private Integer defaultPetHappyMin;
    private Integer defaultPetHappyMax;
    private Integer defaultPetHappyDangerReps;

    private Integer defaultPetTiredMin;
    private Integer defaultPetTiredMax;
    private Integer defaultPetTiredDangerReps;

    private Integer defaultPetHungryMin;
    private Integer defaultPetHungryMax;
    private Integer defaultPetHungryDangerReps;

    private Integer defaultPetActionAgeMin;

    private Integer defaultPetHabitatDomedCityId;
    private Integer defaultPetHabitatSanctuaryId;
    private Integer defaultPetHabitatEscapeId;





}
