package com.mycompany.jobsapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/callback/token").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs/newest").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs", "/api/jobs/**").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .antMatchers(HttpMethod.PUT, "/api/jobs/search").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .antMatchers("/api/jobs", "/api/jobs/**").hasAuthority(JOBS_STAFF)
                .antMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        http.oauth2ResourceServer().jwt();
        http.cors().and().csrf().disable();
    }

    private static final String JOBS_STAFF = "JOBS_STAFF";
    private static final String JOBS_CUSTOMER = "JOBS_CUSTOMER";
}
