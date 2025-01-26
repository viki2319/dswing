package com.example.pgsql

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(userId: Long): User? = userRepository.findById(userId).orElseThrow { ResourceNotFoundException("User not found") }

    fun createUser(user: User): User = userRepository.save(user)

    fun updateUser(userId: Long, updatedUser: User): User {
        if (userRepository.existsById(userId)) {
            val userToUpdate = updatedUser.copy(userId = userId)
            return userRepository.save(userToUpdate)
        }
        throw Exception("User not found")
    }

    fun deleteUser(userId: Long) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
        } else {
            throw Exception("User not found")
        }
    }
    fun searchUsersByName(name: String): List<User> {  //for searching user  by name
        return userRepository.findByNameContainingIgnoreCase(name)
    }



}
