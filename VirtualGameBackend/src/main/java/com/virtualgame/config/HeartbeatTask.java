package com.virtualgame.config;

import com.virtualgame.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatTask {

    private final AppProperties appProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRateString = "${app-config.heartbeatTimeInterval}")
    public void sendHeartbeat() {

        if (appProperties.getHeartbeatIsActive()) {
            String appUrl = appProperties.getHeartbeatAddressHost();
            String endpoint = appProperties.getHeartbeatEndPoint();
            try {
                String respuesta = restTemplate.getForObject(appUrl + endpoint, String.class);
                log.debug("Ping enviado con éxito para mantener la aplicación activa. Respuesta: {}.", respuesta);
            } catch (Exception e) {
                log.debug("Error al enviar el ping: {} {}", appUrl + endpoint, e.getMessage());
            }
        }
    }
}