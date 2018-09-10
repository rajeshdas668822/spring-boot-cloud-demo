package com.spring.cloud.zuul.gateway.zuulgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("password"))
                .roles("USER").and().withUser("admin").password(passwordEncoder.encode("admin"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/book-service/books")
                .permitAll().antMatchers("/eureka/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().formLogin().and()
                .logout().permitAll().logoutSuccessUrl("/book-service/books")
                .permitAll().and().csrf().disable();
    }

}
