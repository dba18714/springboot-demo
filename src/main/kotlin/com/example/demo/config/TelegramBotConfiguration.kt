package com.example.demo.config

import com.example.demo.bot.EchoBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramBotConfiguration {

    @Bean
    fun telegramBotsApi(echoBot: EchoBot): TelegramBotsApi {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        try {
            botsApi.registerBot(echoBot)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return botsApi
    }
}
