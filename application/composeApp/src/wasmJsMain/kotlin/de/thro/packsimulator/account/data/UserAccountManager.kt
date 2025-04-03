package de.thro.packsimulator.account.data

object UserAccountManager {
    // A mutable list to store registered users
    private val registeredUsers = mutableListOf<UserAccount>()

    /**
     * Registers a new user.
     * @param username The username for the new account.
     * @param password The password for the new account.
     * @return Result of the registration as a string message.
     */
    fun registerUser(username: String, password: String): String {
        // Validate the username
        if (username.isBlank()) {
            return "Username cannot be empty"
        }

        // Validate the password
        if (!isValidPassword(password)) {
            return "Password must be at least 8 characters long, include an uppercase letter, a number, and a special character"
        }

        // Check if a user with the same username already exists
        if (registeredUsers.any { it.username == username }) {
            return "Username already exists"
        }

        // Add the new user to the list
        registeredUsers.add(UserAccount(username, password))
        return "Registration successful"
    }

    /**
     * Logs in a user by checking the username and password.
     * @param username The username to log in.
     * @param password The password to log in.
     * @return True if login was successful, false otherwise.
     */
    fun loginUser(username: String, password: String): Boolean {
        // Check if the username and password match an existing user
        return registeredUsers.any { it.username == username && it.password == password }
    }

    /**
     * Validates if the password meets typical password conventions.
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    private fun isValidPassword(password: String): Boolean {
        // Password must be at least 8 characters long, contain at least one uppercase letter,
        // one number, and one special character
        val passwordRegex =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$".toRegex()
        return password.matches(passwordRegex)
    }
}
