package com.example.pgsql

import org.springframework.stereotype.Service

@Service
class AuditLogService(private val auditLogRepository: AuditLogRepository) {

    fun saveAuditLog(auditLog: AuditLog): AuditLog {
        return auditLogRepository.save(auditLog)
    }
}
