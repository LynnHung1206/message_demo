package com.lynn.message_demo.action.event;

import com.lynn.message_demo.dto.LineInDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Lynn on 2024/11/28
 */
@Setter(onMethod_ = {@Autowired})
public abstract class LineEventAction {

  public abstract void execEventProcess(LineInDto lineInDto);
}
