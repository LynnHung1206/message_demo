package com.lynn.message_demo.dao;

import com.lynn.message_demo.vo.LineAccountVo;
import com.lynn.message_demo.vo.LineMessageVo;
import com.lynn.message_demo.vo.TestUserVo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Lynn on 2024/11/26
 */

@Slf4j
@SpringBootTest
class TestUserDaoTest {

  @Autowired
  private LineAccountDao lineAccountDao;

  @Autowired
  private LineMessageDao lineMessageDao;


  @Test
  public void testFindAll() {
//    List<LineAccountVo> results = lineAccountDao.findAll();
//    System.out.println(results);
//    List<LineAccountVo> map = lineAccountDao.selectByMap(Map.of("channel_Id", "1661399198"));
//    log.info("map={}", map);
    List<LineMessageVo> msgByAccount = lineMessageDao.findMsgByAccount(1L);
    log.info("msgByAccount={}", msgByAccount);
  }

}