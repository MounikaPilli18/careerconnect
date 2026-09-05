package com.mounika.careerconnect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception {

        http
    .csrf(csrf -> csrf.disable())

    .cors(cors -> cors.configurationSource(corsConfigurationSource()))

    .authenticationProvider(authenticationProvider)

            .authorizeHttpRequests(auth -> auth

            	    // Public registration
            	    .requestMatchers(
            	        "/users/register",
            	        "/students/register",
            	        "/companies/register"
            	    ).permitAll()

            	    // Swagger / OpenAPI
            	    .requestMatchers(
            	        "/swagger-ui/**",
            	        "/swagger-ui.html",
            	        "/v3/api-docs/**"
            	    ).permitAll()

            	    // Student profile
            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/students/me"
            	    ).hasRole("STUDENT")

            	    // Company profile
            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/companies/me"
            	    ).hasRole("COMPANY")

            	    // Anyone authenticated can view jobs
            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/jobs/**"
            	    ).authenticated()

            	    // Only companies can manage jobs
            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/jobs"
            	    ).hasRole("COMPANY")

            	    .requestMatchers(
            	        HttpMethod.PUT,
            	        "/jobs/**"
            	    ).hasRole("COMPANY")

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/jobs/**"
            	    ).hasRole("COMPANY")

            	    // Everything else requires authentication
            	    .anyRequest().authenticated()
            	)

            .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
@Bean
public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
        List.of("http://localhost:5173")
    );

    configuration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
    );

    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Type", "Accept")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
}
}