package com.alvaro.demo.Security;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
//EnableWebSecurity makes IntelliJ detect the http bean
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    @Autowired
    private UserRepositoryAuthProvider authProvider;


    @Autowired
    @Qualifier("oauth2authSuccessHandler")
    private AuthenticationSuccessHandler oauth2authSuccessHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainApi(HttpSecurity http) throws Exception{
        http.formLogin(form -> 
                form.disable())
            .httpBasic()
            .and().csrf().ignoringRequestMatchers("/api/**")
            .and().authorizeHttpRequests(request ->
            request.requestMatchers(HttpMethod.GET,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.POST,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/api/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE,"/api/**").authenticated()
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChainWeb(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
            request.requestMatchers("/panel","/blog/post","/blog/*").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .requestMatchers("/blog").authenticated()
                .requestMatchers("/**").permitAll())
        .authenticationProvider(authProvider)
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/error")
            .usernameParameter("username")
            .passwordParameter("password"))
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/"))
        .oauth2Login(oauth -> oauth
            .loginPage("/login")
            .successHandler(oauth2authSuccessHandler))
        .csrf().ignoringRequestMatchers("/api/**")
        .and().headers().xssProtection();

        return http.build();      

    }
}