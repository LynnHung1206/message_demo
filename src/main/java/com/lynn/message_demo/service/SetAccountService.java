package com.lynn.message_demo.service;

import com.lynn.message_demo.dao.LineAccountDao;
import com.lynn.message_demo.util.DaoValidationUtil;
import com.lynn.message_demo.vo.LineAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Lynn on 2024/11/26
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SetAccountService {

  private final LineAccountDao lineAccountDao;

  public void insertNewAccount(LineAccountVo lineAccountVo) {
    DaoValidationUtil.validateResultIsOne(() -> lineAccountDao.insert(lineAccountVo));
  }

}
