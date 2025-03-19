package com.lynn.message_demo.action.message.messageDetail;

import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.LocationMessageContent;
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
 * @Author: Lynn on 2024/7/23
 */
@Service(LineMessageActionType.LOCATION)
@RequiredArgsConstructor
@Slf4j
public class LocationMessageAction extends LineMessageAction {
  @Override
  @SneakyThrows
  public void execMessageProcess(LineInDto lineInDto) {
    LocationMessageContent locationMessageContent = Optional.ofNullable(lineInDto.getEvent())
        .map(event -> ((MessageEvent) event))
        .map(MessageEvent::message)
        .map(messageContent -> (LocationMessageContent) messageContent)
        .orElseThrow(RuntimeException::new);
    log.info("locationMessageContent={}", locationMessageContent);

    LineMessageVo lineMessageVo = new LineMessageVo();
    lineMessageVo.setMessage(lineObjectMapper.writeValueAsString(locationMessageContent));
    lineMessageVo.setMessageId(locationMessageContent.id());
    lineMessageVo.setCreateTimestamp(lineInDto.getTimestamp());
    lineMessageVo.setAcNum(1L);
    DaoValidationUtil.validateResultIsOne(() -> lineMessageDao.insert(lineMessageVo));
  }
}