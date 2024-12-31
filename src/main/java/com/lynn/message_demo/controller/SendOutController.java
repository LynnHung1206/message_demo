package com.lynn.message_demo.controller;

import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.PushMessageRequest;
import com.linecorp.bot.messaging.model.PushMessageResponse;
import com.linecorp.bot.messaging.model.TextMessage;
import com.lynn.message_demo.properties.SelfLineProperties;
import io.lettuce.core.api.push.PushMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

/**
 * @Author: Lynn on 2024/12/31
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class SendOutController {

  private final SelfLineProperties selfLineProperties;

  @GetMapping("/send")
  public void send() {
    TextMessage textMessage = new TextMessage
        .Builder("hi hi")
        .build();
    PushMessageRequest messageRequest = new PushMessageRequest.Builder(selfLineProperties.getToken(), Collections.singletonList(textMessage))
        .build();
    MessagingApiClient apiClient = MessagingApiClient.builder(selfLineProperties.getAuth()).build();
    Result<PushMessageResponse> join = apiClient.pushMessage(UUID.randomUUID(), messageRequest).join();
    log.info("join={}", join);
  }
}
