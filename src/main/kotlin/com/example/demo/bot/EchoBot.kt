package com.example.demo.bot

import com.example.demo.config.BotConfig
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.Serializable

@Component
class EchoBot(private val botConfig: BotConfig) : TelegramWebhookBot() {

    override fun getBotToken(): String = botConfig.token

    override fun getBotUsername(): String = botConfig.username

    override fun getBotPath(): String = "/webhook"

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<out Serializable>? {
        return try {
            if (update.hasMessage() && update.message.hasText()) {
                val chatId = update.message.chatId.toString()
                val messageText = update.message.text
                
                // 创建回声消息
                val echoText = when {
                    messageText.startsWith("/start") -> "欢迎使用回声机器人！发送任何消息，我会重复给你。"
                    messageText.startsWith("/help") -> "这是一个回声机器人，会重复你发送的消息。\n命令：\n/start - 开始\n/help - 帮助"
                    else -> "回声：$messageText"
                }
                
                SendMessage.builder()
                    .chatId(chatId)
                    .text(echoText)
                    .build()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
