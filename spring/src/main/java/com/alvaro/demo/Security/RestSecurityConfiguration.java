package com.alvaro.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


public class RestSecurityConfiguration {
    
    @Autowired
    private UserRepositoryAuthProvider authProvider;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
        .and().csrf().disable().httpBasic()
        .and().formLogin().disable();
        /*http.authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().requestMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.csrf().disable();
        http.httpBasic();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
        return http.build();
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

}
