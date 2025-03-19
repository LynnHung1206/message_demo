package com.lynn.message_demo.action.message.messageDetail;

import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.StickerMessageContent;
import com.lynn.message_demo.action.message.LineMessageAction;
import com.lynn.message_demo.action.message.LineMessageActionType;
import com.lynn.message_demo.dto.LineInDto;
import com.lynn.message_demo.util.DaoValidationUtil;
import com.lynn.message_demo.vo.LineMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Lynn on 2025/3/12
 */
@Service(LineMessageActionType.STICKER)
@RequiredArgsConstructor
@Slf4j
public class StickerMessageAction extends LineMessageAction {
  @Override
  @SneakyThrows
  public void execMessageProcess(LineInDto lineInDto) {
    StickerMessageContent stickerMessageContent = Optional.ofNullable(lineInDto.getEvent())
        .map(event -> ((MessageEvent) event))
        .map(MessageEvent::message)
        .map(messageContent -> (StickerMessageContent) messageContent)
        .orElseThrow(RuntimeException::new);
    log.info("stickerMessageContent={}", stickerMessageContent);

    LineMessageVo lineMessageVo = new LineMessageVo();
    lineMessageVo.setMessage(lineObjectMapper.writeValueAsString(stickerMessageContent));
    lineMessageVo.setMessageId(stickerMessageContent.id());
    lineMessageVo.setQuoteToken(stickerMessageContent.quoteToken());
    lineMessageVo.setCreateTimestamp(lineInDto.getTimestamp());
    lineMessageVo.setAcNum(1L);
    DaoValidationUtil.validateResultIsOne(() -> lineMessageDao.insert(lineMessageVo));
  }
}