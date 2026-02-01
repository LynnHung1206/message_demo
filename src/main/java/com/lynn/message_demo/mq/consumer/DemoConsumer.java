package com.lynn.message_demo.mq.consumer;

import com.lynn.message_demo.config.RabbitMQConfiguration;
import com.lynn.message_demo.mq.dto.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 訊息消費者 (Consumer)
 *
 * <h3>Consumer 的職責：</h3>
 * <ul>
 *   <li>監聽指定的 Queue</li>
 *   <li>接收並處理訊息</li>
 *   <li>確認訊息已處理（ack）或拒絕（nack/reject）</li>
 * </ul>
 *
 * <h3>@RabbitListener 註解說明：</h3>
 * <ul>
 *   <li><b>queues</b> - 要監聽的 Queue 名稱</li>
 *   <li><b>concurrency</b> - (未設定) 併發消費者數量，如 "3-10" 表示 3~10 個</li>
 *   <li><b>ackMode</b> - (未設定) 確認模式，預設使用 application.properties 設定</li>
 * </ul>
 *
 * <h3>訊息確認機制 (Acknowledgement)：</h3>
 * <ul>
 *   <li><b>AUTO</b> - 方法正常返回自動 ack，拋例外自動 nack（本專案使用）</li>
 *   <li><b>MANUAL</b> - 手動呼叫 channel.basicAck() / basicNack()</li>
 *   <li><b>NONE</b> - 不確認，訊息送出即刪除（可能遺失）</li>
 * </ul>
 *
 * <h3>失敗處理流程：</h3>
 * <pre>
 * Consumer 處理訊息
 *    ↓ 成功 → 自動 ack → 訊息從 Queue 移除
 *    ↓ 失敗 (拋例外) → 重試 (最多 3 次，見 application.properties)
 *    ↓ 重試失敗 → nack + requeue=false → 進入 Dead Letter Queue
 * </pre>
 *
 * @Author: Lynn
 * @see RabbitMQConfiguration
 * @see DemoProducer
 */
@Component
@Slf4j
public class DemoConsumer {

  /**
   * 監聽 Demo Queue (Direct Exchange)
   *
   * <p>接收來自 demo.direct.exchange 的訊息
   * <br>routing key 必須精確匹配 "demo.routing.key"
   *
   * @param message 自動反序列化的訊息物件
   */
  @RabbitListener(queues = RabbitMQConfiguration.DEMO_QUEUE)
  public void handleDemoMessage(DemoMessage message) {
    log.info("[Consumer] Received from Demo Queue: {}", message);
    processMessage(message);
  }

  /**
   * 監聽 LINE Event Queue (Topic Exchange)
   *
   * <p>接收來自 demo.topic.exchange 的訊息
   * <br>routing key 匹配模式 "line.event.*"
   * <br>例如：line.event.follow, line.event.message, line.event.postback
   *
   * @param message 自動反序列化的訊息物件
   */
  @RabbitListener(queues = RabbitMQConfiguration.LINE_EVENT_QUEUE)
  public void handleLineEvent(DemoMessage message) {
    log.info("[Consumer] Received LINE Event: {}", message);
    processMessage(message);
  }

  /**
   * 監聽 LINE Message Queue (Topic Exchange)
   *
   * <p>接收來自 demo.topic.exchange 的訊息
   * <br>routing key 匹配模式 "line.message.#"
   * <br>例如：line.message.text, line.message.image, line.message.image.large
   *
   * @param message 自動反序列化的訊息物件
   */
  @RabbitListener(queues = RabbitMQConfiguration.LINE_MESSAGE_QUEUE)
  public void handleLineMessage(DemoMessage message) {
    log.info("[Consumer] Received LINE Message: {}", message);
    processMessage(message);
  }

  /**
   * 監聽 Dead Letter Queue (DLQ)
   *
   * <p>接收所有處理失敗的訊息，可用於：
   * <ul>
   *   <li>記錄失敗原因到資料庫</li>
   *   <li>發送告警通知（Email、Slack 等）</li>
   *   <li>人工檢視後決定是否重新發送</li>
   *   <li>定時批次重試</li>
   * </ul>
   *
   * @param message 處理失敗的訊息
   */
  @RabbitListener(queues = RabbitMQConfiguration.DLQ_QUEUE)
  public void handleDeadLetter(DemoMessage message) {
    log.warn("[Consumer] Received Dead Letter: {}", message);
    // TODO: 實作失敗訊息處理邏輯
    // 例如：寫入資料庫、發送告警、重新排程等
  }

  /**
   * 訊息處理邏輯
   *
   * <p>當 type = "error" 時會故意拋出例外，用於測試 DLQ 機制
   *
   * @param message 待處理的訊息
   * @throws RuntimeException 當 type = "error" 時拋出，觸發 DLQ
   */
  private void processMessage(DemoMessage message) {
    // 模擬處理失敗的情況（用於測試 DLQ）
    if ("error".equals(message.getType())) {
      throw new RuntimeException("Simulated processing error for DLQ test");
    }
    log.info("[Consumer] Successfully processed message: {}", message.getId());
  }
}
