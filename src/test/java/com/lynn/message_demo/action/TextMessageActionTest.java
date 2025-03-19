package com.lynn.message_demo.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.webhook.model.DeliveryContext;
import com.linecorp.bot.webhook.model.EventMode;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import com.lynn.message_demo.action.message.LineMessageAction;
import com.lynn.message_demo.action.message.messageDetail.TextMessageAction;
import com.lynn.message_demo.dao.LineMessageDao;
import com.lynn.message_demo.dto.LineInDto;
import com.lynn.message_demo.vo.LineMessageVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author: Lynn on 2025/3/19
 */

@ExtendWith(MockitoExtension.class)
public class TextMessageActionTest {

  @Mock
  private LineMessageDao lineMessageDao;


  @InjectMocks
  private TextMessageAction textMessageAction;

  @Spy
  private ObjectMapper lineObjectMapper = new ObjectMapper();


  private LineInDto lineInDto;
  private MessageEvent messageEvent;
  private TextMessageContent textMessageContent;

  @BeforeEach
  void setUp() {
    textMessageContent = new TextMessageContent.Builder("123456", "Hello, world!", "qt123")
        .build();

    messageEvent = new MessageEvent.Builder(Instant.now().getEpochSecond(),
        EventMode.ACTIVE,
        null,
        new DeliveryContext.Builder(true).build(),
        textMessageContent)
        .build();

    lineInDto = new LineInDto();
    lineInDto.setEvent(messageEvent);
    lineInDto.setTimestamp(Instant.now().toEpochMilli());

    when(lineMessageDao.insert(any())).thenReturn(1);
  }

  @Test
  void testExecMessageProcess() {
    textMessageAction.execMessageProcess(lineInDto);

    ArgumentCaptor<LineMessageVo> captor = ArgumentCaptor.forClass(LineMessageVo.class);
    verify(lineMessageDao, times(1)).insert(captor.capture());

    LineMessageVo captured = captor.getValue();
    assertEquals("123456", captured.getMessageId());
    assertEquals("qt123", captured.getQuoteToken());
    assertEquals(lineInDto.getTimestamp(), captured.getCreateTimestamp());
    assertEquals(1L, captured.getAcNum());
  }

}