package com.mycompany.jobsapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/jobs/last6").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs").hasAnyAuthority("JOBS_CUSTOMER", "JOBS_STAFF", "SCOPE_groups")
                .antMatchers(HttpMethod.GET, "/api/jobs/*").hasAnyAuthority("JOBS_CUSTOMER", "JOBS_STAFF")
                .antMatchers(HttpMethod.POST, "/api/jobs").hasAuthority("JOBS_STAFF")
                .antMatchers(HttpMethod.PUT, "/api/jobs/*").hasAuthority("JOBS_STAFF")
                .antMatchers(HttpMethod.DELETE, "/api/jobs/*").hasAuthority("JOBS_STAFF")
                .anyRequest().permitAll()
                .and().oauth2ResourceServer().jwt();
    }
}
