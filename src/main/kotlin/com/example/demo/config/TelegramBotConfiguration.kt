package com.example.demo.config

import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfiguration {
    // Webhook Bot 不需要通过 TelegramBotsApi 注册
    // Spring Boot 会自动处理 @Component 注解的 TelegramWebhookBot
}
