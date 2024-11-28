package com.lynn.message_demo.service;

import com.lynn.message_demo.dao.LineAccountDao;
import com.lynn.message_demo.vo.LineAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Lynn on 2024/11/28
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class LineWebhookService {

  private final LineAccountDao lineAccountDao;

  public String getChannelSecret(String channelId){
    List<LineAccountVo> map = lineAccountDao.selectByMap(Map.of("channel_Id", channelId));
    return map.getFirst().getChannelSecret();
  }
}
