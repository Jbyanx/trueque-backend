package com.ingsoft.trueque.config.security;

import com.ingsoft.trueque.config.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                            corsConfig.setAllowedOrigins(List.of("http://localhost:5500", "http://127.0.0.1:5500"));
                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
                            corsConfig.setAllowedHeaders(List.of("*"));
                            corsConfig.setAllowCredentials(true);
                            return corsConfig;
                        })
                )
                .csrf(config -> config.disable()) //se usa es en statefull por eso lo deshabilitamos
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider)
                .authorizeHttpRequests(authorizeRequests ->{
                    authorizeRequests.requestMatchers(HttpMethod.POST,"/auth/registrar").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.GET,"/uploads/**").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.GET,"/categorias").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.GET,"/articulos").permitAll();
                    authorizeRequests.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "/webjars/**",
                            "/configuration/**"
                    ).permitAll();

                    authorizeRequests.requestMatchers("/error").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(corsConfigurationSource), JwtAuthenticationFilter.class) // Agregamos el CorsFilter
                .build();
    }
}
