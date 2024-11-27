package com.lynn.message_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lynn.message_demo.vo.LineAccountVo;

import java.util.List;


/**
 * @Author: Lynn on 2024/11/26
 */

public interface LineAccountDao extends BaseMapper<LineAccountVo> {
  List<LineAccountVo> findAll();

}
