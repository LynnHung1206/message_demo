package com.lynn.message_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lynn.message_demo.vo.LineAccountVo;
import com.lynn.message_demo.vo.LineMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lynn on 2024/11/26
 */

public interface LineMessageDao extends BaseMapper<LineMessageVo> {

  List<LineMessageVo> findAll();

  List<LineMessageVo> findMsgByAccount(@Param("acNum")Long acNum);

}
