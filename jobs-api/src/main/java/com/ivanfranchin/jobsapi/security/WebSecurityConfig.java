package com.ivanfranchin.jobsapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/callback/token").permitAll()
                .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/jobs/newest").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/jobs", "/api/jobs/**").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .requestMatchers(HttpMethod.PUT, "/api/jobs/search").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .requestMatchers("/api/jobs", "/api/jobs/**").hasAuthority(JOBS_STAFF)
                .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        http.oauth2ResourceServer().jwt();
        http.cors().and().csrf().disable();
        return http.build();
    }

    private static final String JOBS_STAFF = "JOBS_STAFF";
    private static final String JOBS_CUSTOMER = "JOBS_CUSTOMER";
}
