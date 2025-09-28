package com.virtualgame.security.backend;

import com.virtualgame.security.user.JwtTokenValidator;
import com.virtualgame.security.user.JwtUtils;
import com.virtualgame.security.user.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configure Security Filter Chain for HTTP Security");
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // PERMITIR OPTIONS PARA CORS PREFLIGHT
                    http.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // Configurar los endpoints públicos
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    http.requestMatchers("/api/test/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/public/**").permitAll();
                    http.requestMatchers("/actuator/**").permitAll();
                    //http.requestMatchers(HttpMethod.POST, "/method/load").permitAll();
                    //http.requestMatchers(HttpMethod.GET, "/method/users").permitAll();
                    //http.requestMatchers("/swagger-ui/**").permitAll();

                    http.requestMatchers(SWAGGER_WHITELIST).permitAll();

                    // Configurar los endpoints privados
                    //http.requestMatchers(HttpMethod.PATCH, "/method/patch").hasAnyAuthority("REFACTOR");
                    //http.requestMatchers(HttpMethod.GET, "/method/get").hasAnyRole ("ADMIN");

                    http.requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers("/api/user/**").hasAnyRole("ADMIN", "DEVELOPER", "USER");

                    // CAMBIAR de denyAll() a authenticated() para el resto
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.debug("Configure Authentication Manager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthenticationService userDetailService) {
        log.debug("Configure Authentication Provider for Authentication Service");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Configure Password Encoder");
        return new BCryptPasswordEncoder();
    }
}