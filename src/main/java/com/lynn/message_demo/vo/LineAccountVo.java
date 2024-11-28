package com.lynn.message_demo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Lynn on 2024/11/26
 */
@Data
@TableName("line_account")
public class LineAccountVo {

  private Long acNum;

  private String acName;

  private String channelId;

  private String channelSecret;

  private String authorizationToken;

  private String isActive;

}
