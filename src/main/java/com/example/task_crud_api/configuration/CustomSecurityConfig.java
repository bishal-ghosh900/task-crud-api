package com.example.task_crud_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public CustomSecurityConfig(AuthenticationProvider authenticationProvider, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/me").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/task-api-docs/**").permitAll()
                        .requestMatchers("swagger-ui/**").permitAll()
                        .requestMatchers("/task-api-docs.html").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(config -> config.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
