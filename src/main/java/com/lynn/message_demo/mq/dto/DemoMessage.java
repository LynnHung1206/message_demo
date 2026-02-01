package com.lynn.message_demo.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * RabbitMQ 訊息 DTO
 *
 * <p>實作 {@link Serializable} 是為了確保物件可以被序列化傳輸。
 * 實際上本專案使用 JSON 序列化（{@link com.lynn.message_demo.config.RabbitMQConfiguration#jsonMessageConverter}），
 * 但保留 Serializable 是良好的實踐。
 *
 * <h3>欄位說明：</h3>
 * <ul>
 *   <li><b>id</b> - 訊息唯一識別碼，用於追蹤和去重</li>
 *   <li><b>content</b> - 訊息內容</li>
 *   <li><b>type</b> - 訊息類型，可用於路由或處理邏輯判斷
 *       <br>特殊值 "error" 會觸發 Consumer 拋出例外（測試 DLQ 用）</li>
 *   <li><b>timestamp</b> - 訊息建立時間</li>
 * </ul>
 *
 * @Author: Lynn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoMessage implements Serializable {

  /** 訊息唯一識別碼（UUID） */
  private String id;

  /** 訊息內容 */
  private String content;

  /** 訊息類型（設為 "error" 可測試 DLQ） */
  private String type;

  /** 訊息建立時間 */
  private LocalDateTime timestamp;
}
