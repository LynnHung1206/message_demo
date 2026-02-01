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
 * LINE 事件處理執行器 - 策略模式 (Strategy Pattern)
 *
 * <p>使用 Spring 的 Map 自動注入功能，將所有 {@link LineEventAction} 的實現類注入到 Map 中：
 * <ul>
 *   <li>Key = Bean 名稱（由 @Service 指定，需與 {@link LineEventActionType} 常量一致）</li>
 *   <li>Value = Bean 實例</li>
 * </ul>
 *
 * <p>執行流程：
 * <ol>
 *   <li>根據 Event 類型設定 lineEventType</li>
 *   <li>從 Map 中取得對應的 Action 並執行</li>
 * </ol>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineEventAction
 * @see LineEventActionType
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class LineEventActionExecutor {

  /**
   * Spring 自動注入所有 LineEventAction 實現類
   * 例如：{"MESSAGE_EVENT" -> MessageEventAction, "FOLLOW_EVENT" -> FollowEventAction, ...}
   */
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
