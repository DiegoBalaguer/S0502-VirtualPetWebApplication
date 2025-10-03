package com.virtualgame.security.backend;

import com.virtualgame.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AppProperties appProperties;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        List<String> originsPatternsList = Arrays.asList(appProperties.getCorsDefaultListAllowedOriginPatterns().split(",")).stream()
                .map(String::trim)
                .toList();

        List<String> headersList = Arrays.asList(appProperties.getCorsDefaultListAllowedHeaders().split(",")).stream()
                .map(String::trim)
                .toList();

        List<String> methodsList = Arrays.asList(appProperties.getCorsDefaultListAllowedMethods().split(",")).stream()
                .map(String::trim)
                .toList();

        // Single values
        config.setAllowCredentials(appProperties.getCorsDefaultAllowCredentials());
        config.setMaxAge(appProperties.getCorsDefaultMaxAge()); // Cache preflight 1 hora

        // List values
        //config.setAllowedOriginPatterns(List.of("http://localhost:3000", "*"));
        config.setAllowedOriginPatterns(originsPatternsList);

        //config.setAllowedHeaders(List.of("*"));
        config.setAllowedHeaders(headersList);


        //config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedMethods(methodsList);

        source.registerCorsConfiguration(appProperties.getCorsDefaultRegisterCorsConfiguration(), config);
        return new CorsFilter(source);
    }

/*
        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();

            // 2. Inyectar listas (dividir el String inyectado por comas)
            // Nota: El método trim() es importante para eliminar espacios en blanco si los hubiera.
            List<String> originsList = Arrays.asList(allowedOriginPatterns.split(",")).stream()
                    .map(String::trim)
                    .toList();

            List<String> headersList = Arrays.asList(allowedHeaders.split(",")).stream()
                    .map(String::trim)
                    .toList();

            List<String> methodsList = Arrays.asList(allowedMethods.split(",")).stream()
                    .map(String::trim)
                    .toList();

            // Aplicar las listas
            config.setAllowedOriginPatterns(originsList);
            config.setAllowedHeaders(headersList);
            config.setAllowedMethods(methodsList);

            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
    }

*/
}
