package com.spring.cloud.config.configserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.krb5.EncryptedData;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth , PasswordEncoder passwordEncoder) throws Exception{

        auth.inMemoryAuthentication().withUser("root")
                .password(passwordEncoder.encode("root123")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll();*/

                http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyRole()
                .and()
                .httpBasic();

        /*http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated().and()
                .httpBasic();*/
    }

}
