package com.lynn.message_demo.mq.producer;

import com.lynn.message_demo.config.RabbitMQConfiguration;
import com.lynn.message_demo.mq.dto.DemoMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ 訊息生產者 (Producer)
 *
 * <h3>Producer 的職責：</h3>
 * <ul>
 *   <li>將訊息發送到指定的 Exchange</li>
 *   <li>指定 routing key 決定訊息路由</li>
 *   <li>不直接與 Queue 互動（由 Exchange 根據 Binding 決定）</li>
 * </ul>
 *
 * <h3>發送流程：</h3>
 * <pre>
 * Producer
 *    ↓ rabbitTemplate.convertAndSend(exchange, routingKey, message)
 * Exchange
 *    ↓ 根據 exchange 類型和 routing key 匹配
 * Queue(s)
 * </pre>
 *
 * <h3>本類別提供的發送方式：</h3>
 * <ul>
 *   <li>{@link #sendDirect} - Direct Exchange，精確匹配</li>
 *   <li>{@link #sendLineEvent} - Topic Exchange，模式 line.event.*</li>
 *   <li>{@link #sendLineMessage} - Topic Exchange，模式 line.message.#</li>
 * </ul>
 *
 * @Author: Lynn
 * @see RabbitMQConfiguration
 * @see DemoConsumer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DemoProducer {

  /**
   * RabbitTemplate - Spring AMQP 提供的訊息發送工具
   * 已在 {@link RabbitMQConfiguration} 中配置 JSON 序列化
   */
  private final RabbitTemplate rabbitTemplate;

  /**
   * 發送訊息到 Direct Exchange
   *
   * <p>Direct Exchange 的特性：
   * <ul>
   *   <li>routing key 必須完全匹配才會路由</li>
   *   <li>適合點對點通訊、RPC 模式</li>
   * </ul>
   *
   * <p>本方法的路由：
   * <br>Exchange: demo.direct.exchange
   * <br>Routing Key: demo.routing.key
   * <br>目標 Queue: demo.queue
   *
   * @param message 訊息內容（會自動序列化為 JSON）
   */
  public void sendDirect(DemoMessage message) {
    log.info("[Producer] Sending to Direct Exchange: {}", message);
    rabbitTemplate.convertAndSend(
        RabbitMQConfiguration.DIRECT_EXCHANGE,  // 目標 Exchange
        RabbitMQConfiguration.DEMO_ROUTING_KEY, // Routing Key
        message                                  // 訊息內容
    );
  }

  /**
   * 發送 LINE 事件到 Topic Exchange
   *
   * <p>Topic Exchange 的特性：
   * <ul>
   *   <li>支援萬用字元匹配（* 和 #）</li>
   *   <li>適合發布訂閱模式、事件驅動架構</li>
   * </ul>
   *
   * <p>本方法會動態組合 routing key：
   * <br>eventType = "follow" → routing key = "line.event.follow"
   * <br>eventType = "message" → routing key = "line.event.message"
   *
   * <p>這些都會被 "line.event.*" 模式匹配，送到 line.event.queue
   *
   * @param eventType 事件類型 (follow, message, postback, join, leave 等)
   * @param message   訊息內容
   */
  public void sendLineEvent(String eventType, DemoMessage message) {
    String routingKey = "line.event." + eventType;
    log.info("[Producer] Sending LINE event [{}]: {}", routingKey, message);
    rabbitTemplate.convertAndSend(
        RabbitMQConfiguration.TOPIC_EXCHANGE,
        routingKey,
        message
    );
  }

  /**
   * 發送 LINE 訊息到 Topic Exchange
   *
   * <p>本方法會動態組合 routing key：
   * <br>messageType = "text" → routing key = "line.message.text"
   * <br>messageType = "image.large" → routing key = "line.message.image.large"
   *
   * <p>這些都會被 "line.message.#" 模式匹配，送到 line.message.queue
   * <br>（# 可以匹配多層，所以 image.large 也會匹配）
   *
   * @param messageType 訊息類型 (text, image, video, audio, file, location, sticker)
   * @param message     訊息內容
   */
  public void sendLineMessage(String messageType, DemoMessage message) {
    String routingKey = "line.message." + messageType;
    log.info("[Producer] Sending LINE message [{}]: {}", routingKey, message);
    rabbitTemplate.convertAndSend(
        RabbitMQConfiguration.TOPIC_EXCHANGE,
        routingKey,
        message
    );
  }
}
