package com.example.pgsql

import com.example.pgsql.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> { //for search endpoint
    fun findByNameContainingIgnoreCase(name: String): List<User>
}
