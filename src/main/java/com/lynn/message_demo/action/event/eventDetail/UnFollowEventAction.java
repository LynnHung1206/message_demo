package com.lynn.message_demo.action.event.eventDetail;

import com.lynn.message_demo.action.event.LineEventAction;
import com.lynn.message_demo.action.event.LineEventActionType;
import com.lynn.message_demo.dto.LineInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Lynn on 2024/11/28
 */
@Service(LineEventActionType.UNFOLLOW)
@RequiredArgsConstructor
@Slf4j
public class UnFollowEventAction extends LineEventAction {
  @Override
  public void execEventProcess(LineInDto lineInDto) {

  }
}
