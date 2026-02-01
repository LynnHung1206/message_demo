package com.lynn.message_demo.controller;

import com.lynn.message_demo.mq.dto.DemoMessage;
import com.lynn.message_demo.mq.producer.DemoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * RabbitMQ Demo Controller
 *
 * <h3>測試 API：</h3>
 * <ul>
 *   <li>GET /mq/send/direct?content=xxx - 發送到 Direct Exchange</li>
 *   <li>GET /mq/send/event/{type}?content=xxx - 發送 LINE 事件</li>
 *   <li>GET /mq/send/message/{type}?content=xxx - 發送 LINE 訊息</li>
 *   <li>GET /mq/send/error - 發送會失敗的訊息（測試 DLQ）</li>
 * </ul>
 *
 * @Author: Lynn
 */
@RestController
@RequestMapping("/mq")
@RequiredArgsConstructor
public class RabbitMQController {

  private final DemoProducer demoProducer;

  /**
   * 發送訊息到 Direct Exchange
   * GET /mq/send/direct?content=Hello
   */
  @GetMapping("/send/direct")
  public Map<String, Object> sendDirect(@RequestParam(defaultValue = "Hello RabbitMQ!") String content) {
    DemoMessage message = buildMessage(content, "direct");
    demoProducer.sendDirect(message);
    return Map.of("status", "sent", "message", message);
  }

  /**
   * 發送 LINE 事件到 Topic Exchange
   * GET /mq/send/event/follow?content=User followed
   */
  @GetMapping("/send/event/{eventType}")
  public Map<String, Object> sendLineEvent(
      @PathVariable String eventType,
      @RequestParam(defaultValue = "LINE event occurred") String content) {
    DemoMessage message = buildMessage(content, "line-event-" + eventType);
    demoProducer.sendLineEvent(eventType, message);
    return Map.of("status", "sent", "eventType", eventType, "message", message);
  }

  /**
   * 發送 LINE 訊息到 Topic Exchange
   * GET /mq/send/message/text?content=Hello from LINE
   */
  @GetMapping("/send/message/{messageType}")
  public Map<String, Object> sendLineMessage(
      @PathVariable String messageType,
      @RequestParam(defaultValue = "LINE message content") String content) {
    DemoMessage message = buildMessage(content, "line-message-" + messageType);
    demoProducer.sendLineMessage(messageType, message);
    return Map.of("status", "sent", "messageType", messageType, "message", message);
  }

  /**
   * 發送會失敗的訊息（測試 Dead Letter Queue）
   * GET /mq/send/error
   */
  @GetMapping("/send/error")
  public Map<String, Object> sendError() {
    DemoMessage message = buildMessage("This will fail", "error");
    demoProducer.sendDirect(message);
    return Map.of("status", "sent", "note", "Check DLQ for this message", "message", message);
  }

  /**
   * 自訂訊息發送
   * POST /mq/send
   */
  @PostMapping("/send")
  public Map<String, Object> sendCustom(@RequestBody DemoMessage message) {
    if (message.getId() == null) {
      message.setId(UUID.randomUUID().toString());
    }
    if (message.getTimestamp() == null) {
      message.setTimestamp(LocalDateTime.now());
    }
    demoProducer.sendDirect(message);
    return Map.of("status", "sent", "message", message);
  }

  private DemoMessage buildMessage(String content, String type) {
    return DemoMessage.builder()
        .id(UUID.randomUUID().toString())
        .content(content)
        .type(type)
        .timestamp(LocalDateTime.now())
        .build();
  }
}
