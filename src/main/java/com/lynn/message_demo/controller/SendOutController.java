package com.lynn.message_demo.controller;

import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.client.MessagingApiClientException;
import com.linecorp.bot.messaging.model.ErrorDetail;
import com.linecorp.bot.messaging.model.PushMessageRequest;
import com.linecorp.bot.messaging.model.PushMessageResponse;
import com.linecorp.bot.messaging.model.SentMessage;
import com.linecorp.bot.messaging.model.TextMessage;
import com.lynn.message_demo.properties.SelfLineProperties;
import com.lynn.message_demo.service.SendOutService;
import io.lettuce.core.api.push.PushMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionException;

/**
 * @Author: Lynn on 2024/12/31
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/send")
public class SendOutController {


  private final SendOutService sendOutService;

  @GetMapping("/text")
  public void send() {
    sendOutService.send();
  }


}
