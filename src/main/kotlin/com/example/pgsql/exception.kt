package com.example.pgsql

    class ResourceNotFoundException(message: String) : RuntimeException(message)
    class BadRequestException(message: String) : RuntimeException(message)
    class InternalServerErrorException(message: String) : RuntimeException(message)
    class ConflictException(message: String) : RuntimeException(message)