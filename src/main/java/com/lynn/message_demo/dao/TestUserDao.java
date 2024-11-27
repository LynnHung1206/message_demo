package com.lynn.message_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lynn.message_demo.vo.LineMessageVo;
import com.lynn.message_demo.vo.TestUserVo;

import java.util.List;

/**
 * @Author: Lynn on 2024/11/26
 */
public interface TestUserDao extends BaseMapper<TestUserVo> {

  List<TestUserVo> findAll();

}
