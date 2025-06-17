package de.thro.packsimulator.backend.handler

import de.thro.packsimulator.backend.exception.InvalidCredentialsException
import de.thro.packsimulator.backend.exception.InvalidPasswordFormatException
import de.thro.packsimulator.backend.exception.InvalidTokenException
import de.thro.packsimulator.backend.exception.UserNotFoundException
import de.thro.packsimulator.backend.exception.UsernameAlreadyTakenException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

  private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

  private fun createErrorResponse(message: String): Map<String, String> {
    return mapOf("error" to message)
  }

  @ExceptionHandler(InvalidPasswordFormatException::class)
  fun handleInvalidPasswordFormat(
      ex: InvalidPasswordFormatException
  ): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse(ex.message ?: "Invalid password format")
    logger.error("Invalid password format: ${ex.message}")
    return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler(UsernameAlreadyTakenException::class)
  fun handleUsernameAlreadyTaken(
      ex: UsernameAlreadyTakenException
  ): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse(ex.message ?: "Username is already taken")
    logger.error("Username already taken: ${ex.message}")
    return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
  }

  @ExceptionHandler(InvalidCredentialsException::class)
  fun handleInvalidCredentials(
      ex: InvalidCredentialsException
  ): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse(ex.message ?: "Invalid username or password")
    logger.error("Invalid username or password: ${ex.message}")
    return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(InvalidTokenException::class)
  fun handleInvalidToken(ex: InvalidTokenException): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse(ex.message ?: "Invalid or expired token")
    logger.error("Invalid token: ${ex.message}")
    return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(UserNotFoundException::class)
  fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse(ex.message ?: "User not found")
    logger.error("User not found: ${ex.message}")
    return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler(Exception::class)
  fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
    val errorResponse = createErrorResponse("An unexpected error occurred")
    logger.error("An unexpected error occurred: ${ex.stackTraceToString()}")
    return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
  }
}
