package com.lynn.message_demo.vo;

import lombok.Data;

/**
 * @Author: Lynn on 2024/11/26
 */
@Data
public class LineMessageVo {

  private Long lineUniqNum;

  private Long msgDtNum;

  private String message;

  private String quoteToken;

  private String messageId;
}
