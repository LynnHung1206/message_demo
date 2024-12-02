package com.lynn.message_demo.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: Lynn on 2024/12/2
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheSortedSetHelper {

  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * 放入資料
   *
   * @param key
   * @param value
   * @param score
   */
  public void add(String key, String value, double score) {
    redisTemplate.opsForZSet().add(key, value, score);
  }

  /**
   * 取得資料（順序）
   *
   * @param key
   * @return
   */
  public Set<Object> getAllDataAsc(String key) {
    return redisTemplate.opsForZSet().range(key, 0, -1);
  }

  /**
   * 取得資料（倒序）
   *
   * @param key
   * @return
   */
  public Set<Object> getAllDataDesc(String key) {
    return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
  }

  /**
   * 取得資料和分數（順）
   *
   * @param key
   * @return
   */
  public Set<ZSetOperations.TypedTuple<Object>> getAllDataWithScoresAsc(String key) {
    return redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
  }

  /**
   * 取得資料和分數（倒）
   *
   * @param key
   * @return
   */
  public Set<ZSetOperations.TypedTuple<Object>> getAllDataWithScoresDesc(String key) {
    return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
  }


}
