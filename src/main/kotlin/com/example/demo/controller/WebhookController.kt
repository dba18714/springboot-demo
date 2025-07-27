package com.example.demo.controller

import com.example.demo.bot.EchoBot
import com.example.demo.config.BotConfig
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramBot
import java.io.Serializable
import jakarta.servlet.http.HttpServletRequest

@RestController
class WebhookController(
    private val echoBot: EchoBot,
    private val botConfig: BotConfig
) {

    @PostMapping("/webhook")
    fun onUpdateReceived(@RequestBody update: Update): ResponseEntity<BotApiMethod<out Serializable>?> {
        val response = echoBot.onWebhookUpdateReceived(update)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/set-webhook")
    fun setWebhook(request: HttpServletRequest): ResponseEntity<String> {
        return try {
            // 动态获取当前访问的域名和协议
            val scheme = if (request.getHeader("X-Forwarded-Proto") != null) {
                request.getHeader("X-Forwarded-Proto")
            } else {
                request.scheme
            }
            
            val serverName = if (request.getHeader("X-Forwarded-Host") != null) {
                request.getHeader("X-Forwarded-Host")
            } else if (request.getHeader("Host") != null) {
                request.getHeader("Host")
            } else {
                "${request.serverName}:${request.serverPort}"
            }
            
            val webhookUrl = "$scheme://$serverName/webhook"
            
            // 设置 webhook
            val setWebhook = SetWebhook.builder()
                .url(webhookUrl)
                .build()
            
            val result = echoBot.execute(setWebhook)
            
            if (result) {
                ResponseEntity.ok("Webhook 设置成功！\nWebhook URL: $webhookUrl")
            } else {
                ResponseEntity.badRequest().body("Webhook 设置失败")
            }
        } catch (e: TelegramApiException) {
            ResponseEntity.badRequest().body("设置 Webhook 时发生错误: ${e.message}")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("发生未知错误: ${e.message}")
        }
    }

    @GetMapping("/webhook-info")
    fun getWebhookInfo(): ResponseEntity<String> {
        return try {
            val webhookInfo = echoBot.getWebhookInfo()
            ResponseEntity.ok("""
                Webhook 信息:
                URL: ${webhookInfo.url}
                是否有自定义证书: ${webhookInfo.hasCustomCertificate}
                待处理更新数量: ${webhookInfo.pendingUpdatesCount}
                最后错误日期: ${webhookInfo.lastErrorDate}
                最后错误消息: ${webhookInfo.lastErrorMessage ?: "无"}
                最大连接数: ${webhookInfo.maxConnections}
                允许的更新类型: ${webhookInfo.allowedUpdates?.joinToString(", ") ?: "全部"}
            """.trimIndent())
        } catch (e: TelegramApiException) {
            ResponseEntity.badRequest().body("获取 Webhook 信息时发生错误: ${e.message}")
        }
    }
}
