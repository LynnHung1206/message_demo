package com.lynn.message_demo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Lynn on 2024/11/26
 */
@Data
@TableName("test_user")
public class TestUserVo {

  private Long id;

  private String name;

  private Long age;

}
