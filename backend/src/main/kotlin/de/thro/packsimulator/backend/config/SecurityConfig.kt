package de.thro.packsimulator.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/") // Apply security to all endpoints
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/api/sets").permitAll() // Allow public access to `/api/sets`
                    .anyRequest().authenticated() // Secure all other endpoints
            }
            .csrf { csrf ->
                csrf.disable() // Disable CSRF explicitly using the new API
            }
            .httpBasic { basic ->
                basic.realmName("Realm") // Configure HTTP Basic authentication with custom realm
            }
        return http.build()
    }
}