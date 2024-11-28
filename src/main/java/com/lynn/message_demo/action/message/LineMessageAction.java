package com.lynn.message_demo.action.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynn.message_demo.dao.LineMessageDao;
import com.lynn.message_demo.dto.LineInDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Lynn on 2024/11/28
 */
@Setter(onMethod_ = {@Autowired})
public abstract class LineMessageAction {

  protected LineMessageDao lineMessageDao;

  protected ObjectMapper lineObjectMapper;

  public abstract void execMessageProcess(LineInDto lineInDto);

}
