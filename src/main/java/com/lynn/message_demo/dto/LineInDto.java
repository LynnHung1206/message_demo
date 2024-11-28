package com.lynn.message_demo.dto;

import com.linecorp.bot.webhook.model.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

/**
 * @Author: Lynn on 2024/11/28
 */
@Getter
@Setter
@ToString
public class LineInDto {

  private String lineEventType;

  private String lineMessageType;

  private Event event;

  private String uuId;

  private String channelId;

  private Long timestamp;

}
