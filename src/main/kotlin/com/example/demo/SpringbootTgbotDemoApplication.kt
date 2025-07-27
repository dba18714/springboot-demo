package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class SpringbootTgbotDemoApplication {

    @RequestMapping("/")
    fun home() = "home"
}

fun main(args: Array<String>) {
    runApplication<SpringbootTgbotDemoApplication>(*args)
}
