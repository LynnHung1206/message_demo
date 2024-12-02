package com.lynn.message_demo.controller;

import com.lynn.message_demo.helper.CacheSortedSetHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

/**
 * @Author: Lynn on 2024/12/2
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {


  private final CacheSortedSetHelper cacheSortedSetHelper;

  @GetMapping("/test")
  public Object test() {

    String key = "order";
    cacheSortedSetHelper.add("order", "A", 1);
    cacheSortedSetHelper.add("order", "D", 4);
    cacheSortedSetHelper.add("order", "C", 9);
    cacheSortedSetHelper.add("order", "B", 87);

    log.info("cacheSortedSetHelper.getAllDataAsc(key)={}", cacheSortedSetHelper.getAllDataAsc(key));
    log.info("cacheSortedSetHelper.getAllDataDesc(key)={}", cacheSortedSetHelper.getAllDataDesc(key));

    cacheSortedSetHelper.getAllDataWithScoresAsc(key).forEach(System.out::println);
    cacheSortedSetHelper.getAllDataWithScoresDesc(key).forEach(System.out::println);

    return ResponseEntity.ok().build();
  }

}
