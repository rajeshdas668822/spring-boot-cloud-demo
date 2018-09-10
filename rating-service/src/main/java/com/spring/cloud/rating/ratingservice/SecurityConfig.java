package com.spring.cloud.rating.ratingservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().authorizeRequests()
                .antMatchers("/ratings").hasRole("USER")
                .antMatchers("/ratings/all").hasAnyRole("USER", "ADMIN").anyRequest()
                .authenticated().and().csrf().disable();

    }
}