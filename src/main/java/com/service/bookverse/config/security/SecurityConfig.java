package com.service.bookverse.config.security;

import com.service.bookverse.feature.auth.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.service.bookverse.feature.auth.model.RoleType.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig
{
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity.
                            csrf(csrf -> csrf.disable()).
                            sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                            authorizeHttpRequests(request ->
                                                    request.requestMatchers(
                                                                            "/auth/login",
                                                                            "/auth/register",
                                                                            "/",
                                                                            "/swagger/**",
                                                                            "/swagger-ui/**",
                                                                            "/v3/api-docs/**",
                                                                            "/api-docs/**").permitAll()
//                                                        requestMatchers("/admin/**").hasRole(ADMIN.name())
//                                                        .requestMatchers("/buyer/**").hasRole(BUYER.name())
//                                                        .requestMatchers("/seller/**").hasRole(SELLER.name())
                                                        .anyRequest().authenticated()
                            ).
                            httpBasic(Customizer.withDefaults()).
                            addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
                            build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(appConfig.getBcryptPasswordEncoder());

        return daoAuthenticationProvider;
    }


}
