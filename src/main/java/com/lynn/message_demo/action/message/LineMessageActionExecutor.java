package com.lynn.message_demo.action.message;

import com.linecorp.bot.webhook.model.AudioMessageContent;
import com.linecorp.bot.webhook.model.FileMessageContent;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import com.linecorp.bot.webhook.model.LocationMessageContent;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.StickerMessageContent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import com.linecorp.bot.webhook.model.VideoMessageContent;
import com.lynn.message_demo.dto.LineInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * LINE 訊息處理執行器 - 策略模式 (Strategy Pattern)
 *
 * <p>使用 Spring 的 Map 自動注入功能，將所有 {@link LineMessageAction} 的實現類注入到 Map 中：
 * <ul>
 *   <li>Key = Bean 名稱（由 @Service 指定，需與 {@link LineMessageActionType} 常量一致）</li>
 *   <li>Value = Bean 實例</li>
 * </ul>
 *
 * <p>執行流程：
 * <ol>
 *   <li>根據 MessageContent 類型設定 lineMessageType</li>
 *   <li>從 Map 中取得對應的 Action 並執行</li>
 * </ol>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineMessageAction
 * @see LineMessageActionType
 */
@RequiredArgsConstructor
@Service
public class LineMessageActionExecutor {

  /**
   * Spring 自動注入所有 LineMessageAction 實現類
   * 例如：{"TEXT_MESSAGE" -> TextMessageAction, "IMAGE_MESSAGE" -> ImageMessageAction, ...}
   */
  private final Map<String, LineMessageAction> lineMessageActionMap;

  public void execMessageProcess(LineInDto lineInDto){
    this.setMessageType(lineInDto);
    lineMessageActionMap.get(lineInDto.getLineMessageType()).execMessageProcess(lineInDto);
  }

  private void setMessageType(LineInDto lineInDto) {
    MessageEvent messageEvent = (MessageEvent) lineInDto.getEvent();
    MessageContent content = messageEvent.message();
    switch (content) {
      case TextMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.TEXT);
      case StickerMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.STICKER);
      case ImageMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.IMAGE);
      case VideoMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.VIDEO);
      case AudioMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.AUDIO);
      case FileMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.FILE);
      case LocationMessageContent messageContent -> lineInDto.setLineMessageType(LineMessageActionType.LOCATION);
      case null, default -> throw new RuntimeException(String.format("unknown line type lineInDto=%s", lineInDto));
    }
  }
}
