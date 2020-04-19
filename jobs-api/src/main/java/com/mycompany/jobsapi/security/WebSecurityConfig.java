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
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs/newest").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs", "/api/jobs/**").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .antMatchers(HttpMethod.PUT, "/api/jobs/search").hasAnyAuthority(JOBS_CUSTOMER, JOBS_STAFF)
                .antMatchers("/api/jobs", "/api/jobs/**").hasAuthority(JOBS_STAFF)
                .antMatchers("/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
        http.csrf().disable();
    }

    private static final String JOBS_STAFF = "JOBS_STAFF";
    private static final String JOBS_CUSTOMER = "JOBS_CUSTOMER";

}
