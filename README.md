# Message Demo

LINE Bot 訊息處理練習專案，整合多種技術棧。

## 技術棧

| 技術 | 版本 | 用途 |
|------|------|------|
| Java | 21 | 開發語言 |
| Spring Boot | 3.3.x | 框架 |
| MyBatis Plus | 3.5.5 | ORM |
| MySQL | 8.0 | 資料庫 |
| Redis | 7 | 快取 |
| RabbitMQ | 3 | 訊息佇列 |
| OpenTelemetry | 2.11.0 | 分散式追蹤 |
| LINE Bot SDK | 9.4.0 | LINE Messaging API |

## 快速開始

### 1. 啟動基礎設施

```bash
cd docker
docker-compose up -d
```

### 2. 啟動應用程式

```bash
# 一般啟動
./mvnw spring-boot:run

# 或使用 OpenTelemetry Agent（追蹤 DB、Redis、HTTP Client）
./scripts/run-with-otel.sh
```

### 3. 服務端點

| 服務 | URL | 帳號/密碼 |
|------|-----|----------|
| 應用程式 | http://localhost:8082 | - |
| RabbitMQ UI | http://localhost:15672 | guest / guest |
| Jaeger UI | http://localhost:16686 | - |

## Docker 服務

| 服務 | Port | 說明 |
|------|------|------|
| MySQL | 3306 | 資料庫 (lynn/lynn) |
| Redis | 6379 | 快取 |
| RabbitMQ | 5672, 15672 | 訊息佇列 |
| PostgreSQL | 5432 | 備用資料庫 |
| OTEL Collector | 4317, 55681 | 追蹤收集器 |
| Jaeger | 16686 | 追蹤 UI |

---

## RabbitMQ Demo

### 架構說明

```
┌─────────────────────────────────────────────────────────────────────┐
│                         Direct Exchange                             │
│  demo.direct.exchange                                               │
│       ↓ routing key: demo.routing.key                               │
│  [demo.queue] ──(失敗)──→ [DLX] ──→ [demo.dlq.queue]               │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                         Topic Exchange                              │
│  demo.topic.exchange                                                │
│       ├─ routing key: line.event.* ──→ [line.event.queue]          │
│       └─ routing key: line.message.# ──→ [line.message.queue]      │
└─────────────────────────────────────────────────────────────────────┘
```

### Exchange 類型說明

| 類型 | 匹配方式 | 使用場景 |
|------|---------|---------|
| **Direct** | 精確匹配 routing key | 點對點通訊、RPC |
| **Topic** | 萬用字元匹配 (* 和 #) | 發布訂閱、事件驅動 |
| **Fanout** | 廣播到所有 Queue | 通知、日誌分發 |

**Topic 萬用字元：**
- `*` - 匹配一個單字（例如 `line.event.*` 匹配 `line.event.follow`）
- `#` - 匹配零或多個單字（例如 `line.message.#` 匹配 `line.message.text.large`）

### 測試 API

#### Direct Exchange（精確匹配）

```bash
# 發送訊息到 demo.queue
curl "http://localhost:8082/mq/send/direct?content=Hello"
```

#### Topic Exchange（LINE 事件）

```bash
# 發送 LINE 事件 (routing key: line.event.follow)
curl "http://localhost:8082/mq/send/event/follow?content=User+followed"

# 發送 LINE 事件 (routing key: line.event.message)
curl "http://localhost:8082/mq/send/event/message?content=User+sent+message"
```

#### Topic Exchange（LINE 訊息）

```bash
# 發送 LINE 訊息 (routing key: line.message.text)
curl "http://localhost:8082/mq/send/message/text?content=Hello+LINE"

# 發送 LINE 訊息 (routing key: line.message.image.large)
curl "http://localhost:8082/mq/send/message/image.large?content=Large+image"
```

#### Dead Letter Queue 測試

```bash
# 發送會失敗的訊息（type=error 會觸發例外）
curl "http://localhost:8082/mq/send/error"

# 訊息會在重試 3 次後進入 DLQ
# 可在 RabbitMQ UI 查看 demo.dlq.queue
```

#### 自訂訊息

```bash
curl -X POST "http://localhost:8082/mq/send" \
  -H "Content-Type: application/json" \
  -d '{"content":"Custom message","type":"custom"}'
```

---

## OpenTelemetry 追蹤

### 方式一：Java Agent（推薦）

自動追蹤所有支援的 library（JDBC、Redis、HTTP Client、RabbitMQ）。

```bash
# 首次執行會自動下載 agent
./scripts/run-with-otel.sh
```

### 方式二：Spring Boot Starter

只追蹤 HTTP Server 入站請求。

```bash
# 修改 application.properties
otel.sdk.disabled=false

# 一般啟動
./mvnw spring-boot:run
```

### 查看追蹤

1. 發送請求產生追蹤資料
2. 開啟 Jaeger UI: http://localhost:16686
3. 選擇 Service: `message-demo`
4. 點擊 Find Traces 查看

---

## LINE Bot 設定

### application.properties

```properties
line.bot.channel-secret=YOUR_CHANNEL_SECRET
line.bot.channel-token=YOUR_CHANNEL_TOKEN
```

### Webhook URL

```
https://your-domain.com/callback
```

---

## 專案結構

```
src/main/java/com/lynn/message_demo/
├── action/
│   ├── event/          # LINE 事件處理（策略模式）
│   └── message/        # LINE 訊息處理（策略模式）
├── config/             # 配置類
│   ├── RabbitMQConfiguration.java
│   ├── RedisConfiguration.java
│   └── OtelConfig.java
├── controller/         # API Controller
├── mq/                 # RabbitMQ
│   ├── consumer/       # 消費者
│   ├── producer/       # 生產者
│   └── dto/            # 訊息 DTO
├── dao/                # 資料存取
├── service/            # 業務邏輯
└── vo/                 # Value Object
```

---

## 注意事項

### MyBatis + MyBatis Plus 版本衝突

會產生 `Invalid value type for attribute 'factoryBeanObjectType'` 例外，
需要額外配置 `mybatis-spring` 版本，請參考 `pom.xml`。

### LINE Bot SDK 版本

此專案使用 LINE Bot SDK 6.x 以上版本，與舊版不相容。
