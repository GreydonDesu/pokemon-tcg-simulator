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
        .cors {}
        .authorizeHttpRequests {
          it.requestMatchers("/api/sets", "/images/*", "/api/accounts/register", "/api/accounts/login")
              .permitAll() // Allow public access to these endpoints
              .anyRequest()
              .authenticated() // Secure all other endpoints
        }
        .httpBasic {} // Enable basic authentication
        .csrf {
          it.disable()
        } // Disable CSRF for simplicity (optional, adjust based on your use case)
    return http.build()
  }
}
