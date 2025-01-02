package com.lynn.message_demo.controller;

import com.lynn.message_demo.service.SendOutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Lynn on 2024/12/31
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/send")
public class SendOutController {


  private final SendOutService sendOutService;

  @PostMapping("/text")
  public void sendText(@RequestBody Map<String,Object> param) {
    sendOutService.sendText(param);
  }

  @PostMapping("/image")
  public void sendImage(@RequestBody Map<String,Object> param){

  }


}
