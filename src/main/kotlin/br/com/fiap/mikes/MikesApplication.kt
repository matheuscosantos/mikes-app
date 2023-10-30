package br.com.fiap.mikes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MikesApplication

fun main(args: Array<String>) {
    runApplication<MikesApplication>(*args)
}
