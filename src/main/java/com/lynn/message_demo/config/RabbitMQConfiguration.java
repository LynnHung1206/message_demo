package com.lynn.message_demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置類
 *
 * <h3>RabbitMQ 核心概念：</h3>
 * <pre>
 * Producer → Exchange → Binding (routing key) → Queue → Consumer
 *
 * [Producer]
 *     ↓ 發送訊息 (指定 exchange + routing key)
 * [Exchange] ─── 根據類型和 routing key 決定路由 ───→ [Queue]
 *                                                        ↓
 *                                                   [Consumer]
 * </pre>
 *
 * <h3>Exchange 類型說明：</h3>
 * <ul>
 *   <li><b>Direct Exchange</b> - 精確匹配 routing key，適合點對點通訊
 *       <br>範例：routing key = "order.create" 只會送到綁定 "order.create" 的 queue</li>
 *   <li><b>Topic Exchange</b> - 模式匹配 routing key，適合發布訂閱
 *       <br>* = 匹配一個單字，# = 匹配零或多個單字
 *       <br>範例："line.event.*" 匹配 line.event.follow, line.event.message
 *       <br>範例："line.#" 匹配 line.event.follow, line.message.text.large</li>
 *   <li><b>Fanout Exchange</b> - 廣播到所有綁定的 queue，忽略 routing key</li>
 *   <li><b>Headers Exchange</b> - 根據 message header 匹配，較少使用</li>
 * </ul>
 *
 * <h3>本 Demo 架構：</h3>
 * <pre>
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │                         Direct Exchange                             │
 * │  demo.direct.exchange                                               │
 * │       ↓ routing key: demo.routing.key                               │
 * │  [demo.queue] ──(失敗)──→ [DLX] ──→ [demo.dlq.queue]               │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │                         Topic Exchange                              │
 * │  demo.topic.exchange                                                │
 * │       ├─ routing key: line.event.* ──→ [line.event.queue]          │
 * │       └─ routing key: line.message.# ──→ [line.message.queue]      │
 * └─────────────────────────────────────────────────────────────────────┘
 * </pre>
 *
 * @Author: Lynn
 * @see DemoProducer
 * @see DemoConsumer
 */
@Configuration
public class RabbitMQConfiguration {

  // ==================== Exchange 名稱 ====================
  /** Direct Exchange - 精確匹配 routing key */
  public static final String DIRECT_EXCHANGE = "demo.direct.exchange";
  /** Topic Exchange - 支援萬用字元匹配 */
  public static final String TOPIC_EXCHANGE = "demo.topic.exchange";
  /** Dead Letter Exchange - 處理失敗訊息的專用 exchange */
  public static final String DLX_EXCHANGE = "demo.dlx.exchange";

  // ==================== Queue 名稱 ====================
  /** Demo Queue - 綁定到 Direct Exchange */
  public static final String DEMO_QUEUE = "demo.queue";
  /** LINE 事件 Queue - 接收 line.event.* 的訊息 */
  public static final String LINE_EVENT_QUEUE = "line.event.queue";
  /** LINE 訊息 Queue - 接收 line.message.# 的訊息 */
  public static final String LINE_MESSAGE_QUEUE = "line.message.queue";
  /** Dead Letter Queue - 儲存處理失敗的訊息 */
  public static final String DLQ_QUEUE = "demo.dlq.queue";

  // ==================== Routing Key ====================
  /** Demo 的 routing key（精確匹配） */
  public static final String DEMO_ROUTING_KEY = "demo.routing.key";
  /** LINE 事件的 routing key 模式（* 匹配一個單字） */
  public static final String LINE_EVENT_ROUTING_KEY = "line.event.*";
  /** LINE 訊息的 routing key 模式（# 匹配零或多個單字） */
  public static final String LINE_MESSAGE_ROUTING_KEY = "line.message.#";

  // ==================== Message Converter ====================

  /**
   * JSON 訊息轉換器，自動將物件序列化/反序列化為 JSON
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * RabbitTemplate - 發送訊息的核心元件
   *
   * <p>使用方式：
   * <pre>
   * // 發送到指定 exchange + routing key
   * rabbitTemplate.convertAndSend(exchange, routingKey, message);
   *
   * // 發送到預設 exchange（直接指定 queue 名稱作為 routing key）
   * rabbitTemplate.convertAndSend(queueName, message);
   * </pre>
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                       MessageConverter jsonMessageConverter) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter);
    return template;
  }

  // ==================== Direct Exchange (簡單範例) ====================
  // 訊息流程：Producer → Direct Exchange → (routing key 精確匹配) → Queue → Consumer

  /**
   * Direct Exchange - 根據 routing key 精確匹配
   */
  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(DIRECT_EXCHANGE);
  }

  /**
   * Demo Queue 配置
   *
   * <p>Queue 參數說明：
   * <ul>
   *   <li><b>durable</b> - 持久化，RabbitMQ 重啟後 queue 還在</li>
   *   <li><b>x-dead-letter-exchange</b> - 訊息處理失敗時轉發到此 exchange</li>
   *   <li><b>x-dead-letter-routing-key</b> - 轉發時使用的 routing key</li>
   *   <li><b>x-message-ttl</b> - (未設定) 訊息存活時間，過期後進入 DLQ</li>
   *   <li><b>x-max-length</b> - (未設定) 最大訊息數量，超過後最舊的進入 DLQ</li>
   * </ul>
   */
  @Bean
  public Queue demoQueue() {
    return QueueBuilder.durable(DEMO_QUEUE)
        .withArgument("x-dead-letter-exchange", DLX_EXCHANGE) // 失敗訊息轉發到 DLX
        .withArgument("x-dead-letter-routing-key", "dlq")     // 轉發時的 routing key
        .build();
  }

  /**
   * 綁定 Queue 到 Exchange
   * 當訊息的 routing key = "demo.routing.key" 時，會被路由到 demo.queue
   */
  @Bean
  public Binding demoBinding(Queue demoQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(demoQueue).to(directExchange).with(DEMO_ROUTING_KEY);
  }

  // ==================== Topic Exchange (LINE 事件範例) ====================
  // 訊息流程：Producer → Topic Exchange → (routing key 模式匹配) → Queue → Consumer
  //
  // 萬用字元說明：
  // * (星號) - 匹配一個單字，例如 "line.event.*" 匹配 "line.event.follow"
  // # (井號) - 匹配零或多個單字，例如 "line.#" 匹配 "line.event.follow" 和 "line.message.text.large"

  /**
   * Topic Exchange - 支援萬用字元的路由匹配
   */
  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE);
  }

  /**
   * LINE 事件 Queue
   *
   * <p>匹配模式：line.event.*
   * <br>匹配範例：line.event.follow, line.event.message, line.event.postback
   * <br>不匹配：line.event.message.text（多了一層）
   */
  @Bean
  public Queue lineEventQueue() {
    return QueueBuilder.durable(LINE_EVENT_QUEUE).build();
  }

  /**
   * LINE 訊息 Queue
   *
   * <p>匹配模式：line.message.#
   * <br>匹配範例：line.message.text, line.message.image, line.message.image.large
   * <br># 可以匹配零或多個單字，所以 line.message 也會匹配
   */
  @Bean
  public Queue lineMessageQueue() {
    return QueueBuilder.durable(LINE_MESSAGE_QUEUE).build();
  }

  /**
   * 綁定 LINE 事件 Queue 到 Topic Exchange
   */
  @Bean
  public Binding lineEventBinding(Queue lineEventQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(lineEventQueue).to(topicExchange).with(LINE_EVENT_ROUTING_KEY);
  }

  /**
   * 綁定 LINE 訊息 Queue 到 Topic Exchange
   */
  @Bean
  public Binding lineMessageBinding(Queue lineMessageQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(lineMessageQueue).to(topicExchange).with(LINE_MESSAGE_ROUTING_KEY);
  }

  // ==================== Dead Letter Queue (失敗訊息處理) ====================
  //
  // DLQ (Dead Letter Queue) 用途：
  // 1. 訊息被拒絕 (basic.reject / basic.nack) 且 requeue=false
  // 2. 訊息 TTL 過期
  // 3. Queue 達到最大長度
  //
  // 流程：原始 Queue → (失敗) → DLX Exchange → DLQ Queue → 人工處理/重試

  /**
   * Dead Letter Exchange
   * 用於接收處理失敗的訊息，再轉發到 DLQ
   */
  @Bean
  public DirectExchange dlxExchange() {
    return new DirectExchange(DLX_EXCHANGE);
  }

  /**
   * Dead Letter Queue
   * 儲存所有處理失敗的訊息，可用於：
   * <ul>
   *   <li>人工檢視失敗原因</li>
   *   <li>定時重試</li>
   *   <li>發送告警通知</li>
   * </ul>
   */
  @Bean
  public Queue dlqQueue() {
    return QueueBuilder.durable(DLQ_QUEUE).build();
  }

  /**
   * 綁定 DLQ 到 DLX Exchange
   */
  @Bean
  public Binding dlqBinding(Queue dlqQueue, DirectExchange dlxExchange) {
    return BindingBuilder.bind(dlqQueue).to(dlxExchange).with("dlq");
  }
}
