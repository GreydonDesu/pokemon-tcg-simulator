package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.User
import de.thro.packsimulator.backend.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): User {
        println("Received request: $user")
        return userService.registerUser(user.username, user.password)
    }


    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): User? {
        return userService.getUser(username)
    }
}
