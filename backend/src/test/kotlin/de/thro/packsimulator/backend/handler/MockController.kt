package de.thro.packsimulator.backend.handler

import de.thro.packsimulator.backend.exception.InvalidCredentialsException
import de.thro.packsimulator.backend.exception.InvalidPasswordFormatException
import de.thro.packsimulator.backend.exception.InvalidTokenException
import de.thro.packsimulator.backend.exception.UserNotFoundException
import de.thro.packsimulator.backend.exception.UsernameAlreadyTakenException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mock")
class MockController {

  @GetMapping("/invalid-password-format")
  fun throwInvalidPasswordFormatException(): Nothing {
    throw InvalidPasswordFormatException("Invalid password format")
  }

  @GetMapping("/username-already-taken")
  fun throwUsernameAlreadyTakenException(): Nothing {
    throw UsernameAlreadyTakenException("Username is already taken")
  }

  @GetMapping("/invalid-credentials")
  fun throwInvalidCredentialsException(): Nothing {
    throw InvalidCredentialsException("Invalid username or password")
  }

  @GetMapping("/invalid-token")
  fun throwInvalidTokenException(): Nothing {
    throw InvalidTokenException("Invalid or expired token")
  }

  @GetMapping("/user-not-found")
  fun throwUserNotFoundException(): Nothing {
    throw UserNotFoundException("User not found")
  }

  @Suppress("TooGenericExceptionThrown")
  @GetMapping("/generic-exception")
  fun throwGenericException(): Nothing {
    throw RuntimeException("Unexpected error")
  }
}
