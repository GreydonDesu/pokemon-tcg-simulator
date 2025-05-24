package de.thro.packsimulator.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // Disable CSRF for development (use in production with caution)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/auth/register").permitAll() // Allow public access to /register
                    .anyRequest().authenticated() // Protect all other endpoints
            }
            .httpBasic { httpBasic -> httpBasic.disable() } // Use other authentication mechanisms in production
        return http.build()
    }
}
