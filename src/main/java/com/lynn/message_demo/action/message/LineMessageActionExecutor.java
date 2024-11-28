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
 * @Author: Lynn on 2024/11/28
 */
@RequiredArgsConstructor
@Service
public class LineMessageActionExecutor {

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
