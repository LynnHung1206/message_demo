package com.lynn.message_demo.controller;

import com.lynn.message_demo.service.SetAccountService;
import com.lynn.message_demo.vo.LineAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Lynn on 2024/11/26
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class SetAccountController {

  private final SetAccountService setAccountService;

  @PostMapping("/setAccount")
  public Object insertNewAccount(@RequestBody LineAccountVo lineAccountVo) {
    setAccountService.insertNewAccount(lineAccountVo);
    return ResponseEntity.ok().build();
  }
}
