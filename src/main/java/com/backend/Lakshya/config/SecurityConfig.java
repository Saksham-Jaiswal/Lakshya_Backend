package com.backend.Lakshya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // We inject the AuthenticationProvider (created in ApplicationConfig) here
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. Public Endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()

                        // 2. Owner Only Actions
                        .requestMatchers(HttpMethod.POST, "/api/shops/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/transactions/stock-in").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/transactions/transfer").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("OWNER")
                        // .requestMatchers("/api/dashboard/**").hasRole("OWNER") // <-- REMOVE THIS LINE (It blocked Salespeople)

                        // 3. Shared Actions (Owner AND Salesperson)
                        // Allow both to see Dashboard Stats
                        .requestMatchers("/api/dashboard/**").hasAnyRole("OWNER", "SALESPERSON")
                        // Allow both to find their shop
                        .requestMatchers(HttpMethod.GET, "/api/shops/salesperson/**").hasAnyRole("OWNER", "SALESPERSON")
                        // Allow both to make sales
                        .requestMatchers(HttpMethod.POST, "/api/transactions/sale").hasAnyRole("SALESPERSON", "OWNER")

                        // 4. Authenticated Actions (Catch-all for other GET requests)
                        .requestMatchers("/api/inventory/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/transactions/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/shops/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").authenticated()

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}