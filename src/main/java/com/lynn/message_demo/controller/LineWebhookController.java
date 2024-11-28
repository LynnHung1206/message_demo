package com.lynn.message_demo.controller;

import com.linecorp.bot.parser.LineSignatureValidator;
import com.linecorp.bot.parser.WebhookParseException;
import com.linecorp.bot.parser.WebhookParser;
import com.linecorp.bot.webhook.model.CallbackRequest;
import com.lynn.message_demo.action.event.LineEventActionExecutor;
import com.lynn.message_demo.dto.LineInDto;
import com.lynn.message_demo.service.LineWebhookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author: Lynn on 2024/11/26
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class LineWebhookController {

  private static final String SIGNATURE_HEADER_NAME = "X-Line-Signature";


  private final LineWebhookService lineWebhookService;

  private final LineEventActionExecutor lineEventActionExecutor;



  @PostMapping(value = "/lineAt/push/{channelId}")
  public void lineWebhook(@PathVariable(required = false) String channelId,
                          @RequestHeader(name = SIGNATURE_HEADER_NAME, required = false) String lineSignature,
                          HttpServletRequest request) throws IOException, WebhookParseException {
    byte[] requestBodyByteArray = StreamUtils.copyToByteArray(request.getInputStream());
    String requestBodyJson = new String(requestBodyByteArray, StandardCharsets.UTF_8);
    String uuid = UUID.randomUUID().toString();
    log.info("Line訊息傳入 -> channelId: {}, uuid:{}, lineSignature: {}, requestBody: {}", channelId, uuid, lineSignature, requestBodyJson);

    String lineChannelSecret = lineWebhookService.getChannelSecret(channelId);
    LineSignatureValidator lineSignatureValidator = new LineSignatureValidator(lineChannelSecret.getBytes());
    if (!lineSignatureValidator.validateSignature(requestBodyByteArray, lineSignature)) {
      throw new RuntimeException("error account");
    }
    WebhookParser webhookParser = new WebhookParser(lineSignatureValidator);
    CallbackRequest callbackRequest = webhookParser.handle(lineSignature, requestBodyByteArray);
    callbackRequest.events().forEach(event -> {
      //  有queue 的話塞進queue比較好 如果處理時常過長 line 會發二次訊息 但練習就先這樣
      LineInDto lineInDto = new LineInDto();
      lineInDto.setEvent(event);
      lineInDto.setTimestamp(System.currentTimeMillis());
      lineEventActionExecutor.execEventProcess(lineInDto);
    });


  }



}
