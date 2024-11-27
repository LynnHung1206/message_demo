package com.lynn.message_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author: Lynn on 2024/11/26
 */
@RestController
@Slf4j
public class LineMessageWebhookController {

  private static final String SIGNATURE_HEADER_NAME = "X-Line-Signature";

  @PostMapping(value = "/lineAt/push/{channelId}")
  public void lineWebhook(@PathVariable(required = false) String channelId,
                          @RequestHeader(name = SIGNATURE_HEADER_NAME, required = false) String lineSignature,
                          HttpServletRequest request) throws IOException {
    byte[] requestBodyByteArray = StreamUtils.copyToByteArray(request.getInputStream());
    String requestBodyJson = new String(requestBodyByteArray, StandardCharsets.UTF_8);
    String uuid = UUID.randomUUID().toString();
    log.info("Line訊息傳入 -> channelId: {}, uuid:{}, lineSignature: {}, requestBody: {}", channelId, uuid, lineSignature, requestBodyJson);
  }

}
