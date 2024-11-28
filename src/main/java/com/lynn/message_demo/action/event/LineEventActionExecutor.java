package com.lynn.message_demo.action.event;

import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.FollowEvent;
import com.linecorp.bot.webhook.model.JoinEvent;
import com.linecorp.bot.webhook.model.LeaveEvent;
import com.linecorp.bot.webhook.model.MemberJoinedEvent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.PostbackEvent;
import com.linecorp.bot.webhook.model.UnfollowEvent;
import com.linecorp.bot.webhook.model.UnsendEvent;
import com.lynn.message_demo.action.event.eventDetail.MemberLeftEventAction;
import com.lynn.message_demo.dto.LineInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Lynn on 2024/11/28
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class LineEventActionExecutor {

  private final Map<String, LineEventAction> lineEventActionMap;

  public void execEventProcess(LineInDto lineInDto){
    this.setEvent(lineInDto);
    lineEventActionMap.get(lineInDto.getLineEventType()).execEventProcess(lineInDto);
  }

  private void setEvent(LineInDto lineInDto) {
    Event event = lineInDto.getEvent();
    switch (event) {
      case MessageEvent messageEvent -> lineInDto.setLineEventType(LineEventActionType.MESSAGE);
      case FollowEvent followEvent -> lineInDto.setLineEventType(LineEventActionType.FOLLOW);
      case UnfollowEvent unfollowEvent -> lineInDto.setLineEventType(LineEventActionType.UNFOLLOW);
      case PostbackEvent postbackEvent -> lineInDto.setLineEventType(LineEventActionType.POSTBACK);
      case JoinEvent joinEvent -> lineInDto.setLineEventType(LineEventActionType.JOIN);
      case LeaveEvent leaveEvent -> lineInDto.setLineEventType(LineEventActionType.LEAVE);
      case UnsendEvent unsendEvent -> lineInDto.setLineEventType(LineEventActionType.UNSEND);
      case MemberJoinedEvent memberJoinedEvent -> lineInDto.setLineEventType(LineEventActionType.MEMBER_JOINED);
      case MemberLeftEventAction memberLeftEventAction -> lineInDto.setLineEventType(LineEventActionType.MEMBER_LEFT);
      case null, default -> throw new RuntimeException(String.format("unknown line event lineInDto=%s", lineInDto));
    }
  }
}
