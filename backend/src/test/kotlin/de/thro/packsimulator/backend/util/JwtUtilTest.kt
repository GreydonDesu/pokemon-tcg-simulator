package de.thro.packsimulator.backend.util

import com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JwtUtilTest {

  @Test
  fun `should generate a valid token`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val username = "testuser"
      val token = jwtUtil.generateToken(username)

      assertNotNull(token, "Token should not be null")
      assertTrue(token.isNotEmpty(), "Token should not be empty")
    }
  }

  @Test
  fun `should extract username from valid token`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val username = "testuser"
      val token = jwtUtil.generateToken(username)

      val extractedUsername = jwtUtil.extractUsername(token)
      assertEquals(
          username, extractedUsername, "Extracted username should match the original username")
    }
  }

  @Test
  fun `should return null for invalid token`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val invalidToken = "invalid.token.value"

      val extractedUsername = jwtUtil.extractUsername(invalidToken)
      assertNull(extractedUsername, "Extracted username should be null for an invalid token")
    }
  }

  @Test
  fun `should validate a valid token`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val username = "testuser"
      val token = jwtUtil.generateToken(username)

      val isValid = jwtUtil.isTokenValid(token, username)
      assertTrue(isValid, "Token should be valid for the correct username")
    }
  }

  @Test
  fun `should invalidate a token with incorrect username`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val username = "testuser"
      val token = jwtUtil.generateToken(username)

      val isValid = jwtUtil.isTokenValid(token, "wronguser")
      assertFalse(isValid, "Token should be invalid for an incorrect username")
    }
  }

  @Test
  fun `should handle malformed token gracefully`() {
    withEnvironmentVariable("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=").execute {
      val jwtUtil = JwtUtil()
      val malformedToken = "malformed.token"

      val extractedUsername = jwtUtil.extractUsername(malformedToken)
      assertNull(extractedUsername, "Extracted username should be null for a malformed token")
    }
  }
}
