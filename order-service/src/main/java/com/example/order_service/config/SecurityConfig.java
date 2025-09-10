package com.example.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/orders/**").authenticated()
//                        .anyRequest().permitAll())
//                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public JwtAuthFilter jwtAuthFilter() {
//        return new JwtAuthFilter();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/orders").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(resource -> resource
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }

}
