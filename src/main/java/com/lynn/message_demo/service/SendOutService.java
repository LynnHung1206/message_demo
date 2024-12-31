package com.lynn.message_demo.service;

import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.client.MessagingApiClientException;
import com.linecorp.bot.messaging.model.AudioMessage;
import com.linecorp.bot.messaging.model.ImageMessage;
import com.linecorp.bot.messaging.model.LocationMessage;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.PushMessageRequest;
import com.linecorp.bot.messaging.model.PushMessageResponse;
import com.linecorp.bot.messaging.model.Sender;
import com.linecorp.bot.messaging.model.StickerMessage;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.messaging.model.VideoMessage;
import com.lynn.message_demo.properties.SelfLineProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionException;

/**
 * @Author: Lynn on 2024/12/31
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SendOutService {


  private final SelfLineProperties selfLineProperties;


  public void send() {
    String text = "你好給約ㄇ";
    String senderName = "Lynnnnnnn";
    Message textMessage = this.genLineTextMessage(text, senderName);
    PushMessageRequest pushMessageRequest = this.genPushMessage(selfLineProperties.getToken(), Collections.singletonList(textMessage));
    MessagingApiClient apiClient = MessagingApiClient.builder(selfLineProperties.getAuth()).build();
    try {
      Result<PushMessageResponse> responseResult = apiClient.pushMessage(UUID.randomUUID(), pushMessageRequest).join();
      PushMessageResponse response = responseResult.body();
      response.sentMessages().forEach(sentMessage -> {
        String quoteToken = sentMessage.quoteToken();
        String id = sentMessage.id();
      });
    } catch (CompletionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof MessagingApiClientException) {
        MessagingApiClientException c = (MessagingApiClientException) cause;
        String error = c.getError();
        System.out.println("error = " + error);
      } else {
        log.error("", e);
      }
    } catch (Exception e) {
      log.error("", e);
    }
  }


  private PushMessageRequest genPushMessage(String toToken, List<Message> messageList) {
    return new PushMessageRequest.Builder(toToken, messageList).build();
  }

  private Message genLineTextMessage(String text, String name) {
    return new TextMessage
        .Builder(text)
        .sender(new Sender.Builder().name(name).build())
        .build();
  }

  private Message genImageMessage(String uri) {
    return new ImageMessage
        .Builder(URI.create(uri), URI.create(uri))
        .build();
  }

  private Message genStickerMessage(String stickerId, String packageId) {
    return new StickerMessage
        .Builder(packageId, stickerId)
        .build();
  }

  private Message genVideoMessage(String uri) {
    return new VideoMessage
        .Builder(URI.create(uri), URI.create(uri))
        .build();
  }

  private Message genAudioMessage(String uri, long duration) {
    return new AudioMessage
        .Builder(URI.create(uri), duration)
        .build();
  }

  private Message genLocationMessage(String title, String address, Double latitude, Double longitude) {
    return new LocationMessage
        .Builder(title, address, latitude, longitude)
        .build();
  }
}