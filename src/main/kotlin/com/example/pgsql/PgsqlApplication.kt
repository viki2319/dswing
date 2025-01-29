package com.example.pgsql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.pulsar.annotation.EnablePulsar

@SpringBootApplication
@EnablePulsar
class PgsqlApplication

fun main(args: Array<String>) {
	runApplication<PgsqlApplication>(*args)
}
