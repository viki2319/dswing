package com.example.pgsql

import jakarta.persistence.*

@Entity
@Table(name = "audit_logs")
data class AuditLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val action: String,
    val description: String,
    val statusCode: Int,
    val timestamp: java.time.LocalDateTime = java.time.LocalDateTime.now()
)
