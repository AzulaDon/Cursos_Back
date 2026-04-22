package com.curso.OmetepecGuerrero.config;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Públicos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cursos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categorias").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/certificados/verificar/**").permitAll()
                
                // Solo Admin
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/docentes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/docentes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/docentes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cursos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/videos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasRole("ADMIN")
                
                // Autenticados
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
