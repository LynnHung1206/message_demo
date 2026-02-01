package com.lynn.message_demo.action.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import com.lynn.message_demo.dao.LineMessageDao;
import com.lynn.message_demo.dto.LineInDto;
import com.lynn.message_demo.util.DaoValidationUtil;
import com.lynn.message_demo.vo.LineMessageVo;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * LINE 訊息處理抽象類 - 策略模式的策略介面
 *
 * <p>所有訊息處理類都必須繼承此抽象類，並使用 {@code @Service(LineMessageActionType.XXX)} 註冊。
 * Spring 會自動將所有實現類注入到 {@link LineMessageActionExecutor} 的 Map 中。
 *
 * <p>實作範例：
 * <pre>
 * {@code @Service(LineMessageActionType.TEXT)}
 * public class TextMessageAction extends LineMessageAction {
 *     @Override
 *     public void execMessageProcess(LineInDto lineInDto) {
 *         // 處理文字訊息
 *     }
 * }
 * </pre>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineMessageActionType
 * @see LineMessageActionExecutor
 */
@Setter(onMethod_ = {@Autowired}) // Lombok: 為所有 setter 方法加上 @Autowired，實現依賴注入
@Slf4j
public abstract class LineMessageAction {

  /** 訊息資料存取物件 */
  protected LineMessageDao lineMessageDao;

  /** JSON 序列化工具（由 line-bot-sdk 提供） */
  protected ObjectMapper lineObjectMapper;

  /**
   * 執行訊息處理邏輯
   *
   * @param lineInDto 包含 LINE 訊息資訊的 DTO
   */
  public abstract void execMessageProcess(LineInDto lineInDto);

}
