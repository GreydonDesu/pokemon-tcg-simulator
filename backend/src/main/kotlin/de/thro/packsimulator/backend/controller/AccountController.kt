package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    // Register a new account
    @PostMapping("/register")
    fun register(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        accountService.registerAccount(username, password)
        return ResponseEntity.ok("Account registered successfully")
    }

    // Login with username and password
    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        accountService.login(username, password)
        return ResponseEntity.ok("Login successful")
    }
}
