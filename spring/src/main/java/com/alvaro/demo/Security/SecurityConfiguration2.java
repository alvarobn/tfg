package com.alvaro.demo.Security;
import java.io.IOException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import ch.qos.logback.core.filter.Filter;




//@Configuration
//@EnableWebSecurity  //mirad deprecated
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration2{

    @Autowired
    private UserRepositoryAuthProvider authProvider;
    
    @Autowired
    @Qualifier("oauth2authSuccessHandler")
    private AuthenticationSuccessHandler oauth2authSuccessHandler;


    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
        http.authorizeHttpRequests(request ->
            request.requestMatchers("/panel","/blog/post","/blog/*").hasAnyRole("ROLE_ADMIN","ROLE_USER")
            .requestMatchers("/blog").authenticated()
            .requestMatchers("/panel","/blog/post","/blog/*").permitAll()
            .requestMatchers("/api/**").permitAll()
            .anyRequest().permitAll())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/error")
                .usernameParameter("username")
                .passwordParameter("password"))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/"))
            .oauth2Login(oauth -> oauth
                .loginPage("/login")
                .successHandler(oauth2authSuccessHandler))
            .csrf().ignoringRequestMatchers("/api/**");

        return http.build();      
    }

    //@Bean
    //@Order(1)
    public SecurityFilterChain filterChainRest(HttpSecurity http) throws Exception{
        http.formLogin(form -> 
                form.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic()
            .and().csrf().ignoringRequestMatchers("/api/**")
            .and().authorizeHttpRequests(request ->
            request.requestMatchers(HttpMethod.GET,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.POST,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE,"/api/**").authenticated());
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}