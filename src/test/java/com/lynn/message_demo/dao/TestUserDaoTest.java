package com.lynn.message_demo.dao;

import com.lynn.message_demo.vo.LineAccountVo;
import com.lynn.message_demo.vo.TestUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Lynn on 2024/11/26
 */

@Slf4j
@SpringBootTest
class TestUserDaoTest {

  @Autowired
  private  TestUserDao testUserDao;

  @Autowired
  private LineAccountDao lineAccountDao;


  @Test
  public void testFindAll() {
    List<LineAccountVo> results = lineAccountDao.findAll();
    System.out.println(results);
  }

}