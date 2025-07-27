# Telegram Echo Bot - Spring Boot Demo

这是一个基于 Spring Boot 和 TelegramBots 库的回声机器人示例，使用 Webhook 方式接收消息。

## 功能特性

- 🤖 回声机器人：重复用户发送的消息
- 🔗 Webhook 方式：高效的消息接收
- 🌐 动态域名检测：自动获取当前访问域名设置 Webhook
- ⚙️ 简单配置：通过环境变量配置机器人

## 快速开始

### 1. 创建 Telegram Bot

1. 在 Telegram 中找到 [@BotFather](https://t.me/botfather)
2. 发送 `/newbot` 创建新机器人
3. 按提示设置机器人名称和用户名
4. 获取 Bot Token

### 2. 配置环境变量

```bash
export TELEGRAM_BOT_TOKEN="your-bot-token-here"
export TELEGRAM_BOT_USERNAME="your-bot-username"
export PORT=8080
```

### 3. 运行应用

```bash
# 使用 Gradle 运行
./gradlew bootRun

# 或者构建后运行
./gradlew build
java -jar build/libs/springboot-tgbot-demo-0.0.1-SNAPSHOT.jar
```

### 4. 设置 Webhook

访问 `http://your-domain.com/set-webhook` 来自动设置 Webhook URL。

应用会自动检测当前访问的域名并设置相应的 Webhook URL。

## API 端点

- `GET /` - 主页，显示机器人状态
- `POST /webhook` - Telegram Webhook 端点
- `GET /set-webhook` - 设置 Webhook（无需参数）
- `GET /webhook-info` - 查看当前 Webhook 信息

## 机器人命令

- `/start` - 开始使用机器人
- `/help` - 显示帮助信息
- 其他任何消息 - 机器人会重复发送

## 部署说明

### 使用 Docker

```bash
# 构建镜像
docker build -t telegram-echo-bot .

# 运行容器
docker run -p 8080:8080 \
  -e TELEGRAM_BOT_TOKEN="your-bot-token" \
  -e TELEGRAM_BOT_USERNAME="your-bot-username" \
  telegram-echo-bot
```

### 部署到云平台

1. **Heroku**: 设置环境变量后直接部署
2. **Railway**: 连接 GitHub 仓库自动部署
3. **Render**: 支持自动 HTTPS，适合 Webhook

## 注意事项

1. **HTTPS 要求**: Telegram Webhook 要求使用 HTTPS，本地测试可以使用 ngrok
2. **域名检测**: 应用会自动检测 `X-Forwarded-Host` 和 `X-Forwarded-Proto` 头，适配反向代理
3. **错误处理**: 包含完整的错误处理和日志记录

## 开发

### 本地测试

使用 ngrok 创建 HTTPS 隧道：

```bash
# 安装 ngrok
npm install -g ngrok

# 创建隧道
ngrok http 8080

# 使用 ngrok 提供的 HTTPS URL 访问 /set-webhook
```

### 项目结构

```
src/main/kotlin/com/example/demo/
├── SpringbootTgbotDemoApplication.kt  # 主应用类
├── bot/
│   └── EchoBot.kt                     # 回声机器人实现
├── config/
│   ├── BotConfig.kt                   # 机器人配置
│   └── TelegramBotConfiguration.kt    # Telegram Bot 配置
└── controller/
    └── WebhookController.kt           # Webhook 控制器
```

## 许可证

MIT License
