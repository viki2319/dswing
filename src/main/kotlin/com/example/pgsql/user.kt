package com.example.pgsql
import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

@Entity
@Table(name="userdata")
data class User(
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    val name: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null
)
class ResourceNotFoundException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)
class InternalServerErrorException(message: String) : RuntimeException(message)
class ConflictException(message: String) : RuntimeException(message)
