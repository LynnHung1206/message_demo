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
 * @Author: Lynn on 2024/11/28
 */
@Setter(onMethod_ = {@Autowired})
@Slf4j
public abstract class LineMessageAction {

  protected LineMessageDao lineMessageDao;

  protected ObjectMapper lineObjectMapper;

  public abstract void execMessageProcess(LineInDto lineInDto);

}
