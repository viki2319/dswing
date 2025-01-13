package com.example.pgsql
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class User1(val id: Long, val name: String, val email: String)

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/search") // for searching user endpoint
    fun searchUsers(@RequestParam name: String): List<User> {
        logger.info("Searching for users with name containing: $name")
        return userService.searchUsersByName(name)
    }

        @GetMapping
        fun getAllUsers(): List<User> {
            // Example: Simulate throwing an exception if there is a problem
            // throw InternalServerErrorException("Internal server error occurred while fetching users.")
            logger.info("fetching all users data")
            return userService.getAllUsers()
        }

        @GetMapping("/{userId}")
        fun getUserById(@PathVariable userId: Long): User? {
            // Example: Throw exception if user not found
            val user = userService.getUserById(userId)
            logger.info("fetching user data @ $userId")
            if (user == null) {
                logger.warn("User with ID $userId not found.")
            }
            return user
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun createUser(@RequestBody user: User): User {
            // Example: Validate user before creating
            if (user.name?.isBlank() == true) {
                logger.info("user name cannot be empty")
            }
            return userService.createUser(user)
        }

        @PutMapping("/{userId}")
        fun updateUser(
            @PathVariable userId: Long,
            @RequestBody user: User
        ): User {
            // Example: Check if user exists before updating
            val existingUser = userService.getUserById(userId)
            if (existingUser == null) {
                logger.error("user not found")
            }
            return userService.updateUser(userId, user)
            logger.info("user with id $userId uodated")
        }

        @DeleteMapping("/{userId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun deleteUser(@PathVariable userId: Long) {
            // Example: Check if user exists before deleting
            val user = userService.getUserById(userId)
            if (user == null) {
                logger.info("user with id $userId not found")
            }
            userService.deleteUser(userId)
            logger.info("user with $userId Deleted")
        }

        // 400 - Bad Request
        @ExceptionHandler(BadRequestException::class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        fun handleBadRequestException(ex: BadRequestException): Map<String, Any?> {
            return mapOf(
                "discription" to ex.message,
                "error_code" to HttpStatus.BAD_REQUEST.value()
            )
        }

        // 404 - Resource Not Found
        @ExceptionHandler(ResourceNotFoundException::class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        fun handleResourceNotFoundException(ex: ResourceNotFoundException): Map<String, Any?> {
            return mapOf(
                "discription" to ex.message,
                "error_code" to HttpStatus.NOT_FOUND.value()
            )
        }

        // 500 - Internal Server Error
        @ExceptionHandler(InternalServerErrorException::class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        fun handleInternalServerErrorException(ex: InternalServerErrorException): Map<String, Any?> {
            return mapOf(
                "discription" to ex.message,
                "error_code" to HttpStatus.INTERNAL_SERVER_ERROR.value()
            )
        }

        // 409 - Conflict
        @ExceptionHandler(ConflictException::class)
        @ResponseStatus(HttpStatus.CONFLICT)
        fun handleConflictException(ex: ConflictException): Map<String, Any?> {
            return mapOf(
                "description" to ex.message,
                "error_code" to HttpStatus.CONFLICT.value()
            )
        }
    }

