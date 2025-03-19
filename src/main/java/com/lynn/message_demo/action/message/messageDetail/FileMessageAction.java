package com.lynn.message_demo.action.message.messageDetail;

import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.FileMessageContent;
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
@Service(LineMessageActionType.FILE)
@RequiredArgsConstructor
@Slf4j
public class FileMessageAction extends LineMessageAction {
  @Override
  @SneakyThrows
  public void execMessageProcess(LineInDto lineInDto) {
    FileMessageContent fileMessageContent = Optional.ofNullable(lineInDto.getEvent())
        .map(event -> ((MessageEvent) event))
        .map(MessageEvent::message)
        .map(messageContent -> (FileMessageContent) messageContent)
        .orElseThrow(RuntimeException::new);
    log.info("fileMessageContent={}", fileMessageContent);

    LineMessageVo lineMessageVo = new LineMessageVo();
    lineMessageVo.setMessage(lineObjectMapper.writeValueAsString(fileMessageContent));
    lineMessageVo.setMessageId(fileMessageContent.id());
    lineMessageVo.setCreateTimestamp(lineInDto.getTimestamp());
    lineMessageVo.setAcNum(1L);
    DaoValidationUtil.validateResultIsOne(() -> lineMessageDao.insert(lineMessageVo));
  }
}