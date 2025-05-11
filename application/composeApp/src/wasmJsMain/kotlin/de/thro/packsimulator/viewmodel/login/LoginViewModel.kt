package de.thro.packsimulator.viewmodel.login

import de.thro.packsimulator.manager.AccountManager
import de.thro.packsimulator.viewmodel.account.AccountViewModel

object LoginViewModel {

    /*
    * Handles user login.
    * @param username The username entered by the user.
    * @param password The password entered by the user.
    * @return A Pair indicating success (Boolean) and an optional error message (String).
    */
    fun loginUser(username: String, password: String): Pair<Boolean, String?> {
        return if (AccountViewModel.loginUser(username, password)) {
            val account = AccountViewModel.getAccountByUsername(username)
            if (account != null) {
                AccountManager.setCurrentAccount(account)
                Pair(true, null) // Login successful
            } else {
                Pair(false, "Login failed! Account not found.")
            }
        } else {
            Pair(false, "Invalid username or password!")
        }
    }

    /*
    * Handles user registration and automatic login.
    * @param username The username entered by the user.
    * @param password The password entered by the user.
    * @return A Pair indicating success (Boolean) and an optional error message (String).
    */
    fun registerUser(username: String, password: String): Pair<Boolean, String?> {
        val result = AccountViewModel.registerUser(username, password)
        return if (result == "Registration successful") {
            val newAccount = AccountViewModel.getAccountByUsername(username)
            if (newAccount != null) {
                AccountManager.setCurrentAccount(newAccount)
                Pair(true, null) // Registration and login successful
            } else {
                Pair(false, "Registration failed! Account not found.")
            }
        } else {
            Pair(false, result) // Registration failed with error message
        }
    }
}