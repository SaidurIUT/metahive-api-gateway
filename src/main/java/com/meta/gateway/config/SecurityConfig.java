package com.meta.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final String[] freeResourceUrls = {
            "/aggregate/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/office-service/v3/api-docs",
            "/**"
        
    };

    // Security Filter Chain Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity.authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(freeResourceUrls)
                            .permitAll()
                            .anyRequest().authenticated())
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                    .build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            // Handle other types of exceptions
            throw new IllegalStateException("Security configuration error: " + e.getMessage(), e);
        }
    }

    // CORS Configuration Source
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
