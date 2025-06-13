package de.thro.packsimulator.backend.exception

class InvalidPasswordFormatException(message: String) : RuntimeException(message)
class UsernameAlreadyTakenException(message: String) : RuntimeException(message)
class InvalidCredentialsException(message: String) : RuntimeException(message)
class InvalidTokenException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)