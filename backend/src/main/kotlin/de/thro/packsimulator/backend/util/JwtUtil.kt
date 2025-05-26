package de.thro.packsimulator.backend.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object JwtUtil {
    private const val EXPIRATION_TIME = 3600000 // 1 hour in milliseconds
    private const val SECRET_KEY_STRING = "YOUR_SECRET_KEY_STRING" // Replace with Base64-encoded secret key

    private val SECRET_KEY: SecretKey = SecretKeySpec(
        Base64.getDecoder().decode(SECRET_KEY_STRING),
        "HmacSHA256" // Explicitly specify the algorithm
    )

    fun generateToken(username: String): String {
        val claims = mapOf("username" to username)
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact()
    }

    // Validate the JWT token and return the username if valid
    fun extractUsername(token: String): String? {
        return try {
            val claims: Claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .payload

            claims.subject // Return the username (stored as the subject in the token)
        } catch (e: Exception) {
            null // Invalid token
        }
    }
}