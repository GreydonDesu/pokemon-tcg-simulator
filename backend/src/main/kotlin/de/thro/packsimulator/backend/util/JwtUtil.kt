package de.thro.packsimulator.backend.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private const val EXPIRATION_TIME = 3600000 // 1 hour in milliseconds

// Use Elvis operator to validate and retrieve the environment variable
private val SECRET_KEY_STRING: String =
    System.getenv("JWT_SECRET") ?: error("JWT_SECRET environment variable is not set")

private const val SECRET_KEY_SIZE = 32

// Decode the Base64-encoded secret key
private val SECRET_KEY: SecretKey =
    try {
      val decodedKey = Base64.getDecoder().decode(SECRET_KEY_STRING)
      require(decodedKey.size >= SECRET_KEY_SIZE) {
        "JWT_SECRET must be at least 32 bytes (256 bits) after Base64 decoding"
      }
      SecretKeySpec(decodedKey, "HmacSHA256")
    } catch (e: IllegalArgumentException) {
      throw IllegalStateException("Invalid Base64 encoding for JWT_SECRET", e)
    }

@Component // Mark this class as a Spring-managed bean
class JwtUtil {

  private val logger: Logger = LoggerFactory.getLogger(JwtUtil::class.java)

  fun generateToken(username: String): String {
    val claims = mapOf("username" to username)
    return Jwts.builder()
        .claims(claims)
        .issuedAt(Date())
        .expiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SECRET_KEY)
        .compact()
  }

    /**
     * Extract the username from the JWT token.
     */
  fun extractUsername(token: String): String? {
    return try {
      val claims: Claims = extractAllClaims(token)
      claims["username"] as String? // Return the username (stored as the subject in the token)
    } catch (e: Exception) {
        logger.error("Failed to extract username from token: {}", e.message)
        null // Invalid token
    }
  }

    /**
     * Validate the token and check if it is expired.
     */
    fun isTokenValid(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return extractedUsername == username && !isTokenExpired(token)
    }

    /**
     * Check if the token is expired.
     */
    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractAllClaims(token).expiration
        return expiration.before(Date())
    }

    /**
     * Extract all claims from the token.
     */
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).payload
    }
}
