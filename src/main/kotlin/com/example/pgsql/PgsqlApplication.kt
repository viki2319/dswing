package com.example.pgsql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PgsqlApplication

fun main(args: Array<String>) {
	runApplication<PgsqlApplication>(*args)
}
