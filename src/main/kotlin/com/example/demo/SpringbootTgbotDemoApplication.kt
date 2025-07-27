package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.config.BotConfig

@RestController
@SpringBootApplication
@EnableConfigurationProperties(BotConfig::class)
class SpringbootTgbotDemoApplication {

    @RequestMapping("/")
    fun home() = """
        <h1>Telegram Echo Bot</h1>
        <p>机器人正在运行中...</p>
        <p><a href="/set-webhook">设置 Webhook</a></p>
        <p><a href="/webhook-info">查看 Webhook 信息</a></p>
    """.trimIndent()
}

fun main(args: Array<String>) {
    runApplication<SpringbootTgbotDemoApplication>(*args)
}
