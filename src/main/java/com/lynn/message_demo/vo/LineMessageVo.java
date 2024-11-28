package com.lynn.message_demo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynn.message_demo.handler.JsonbTypeHandler;
import com.lynn.message_demo.handler.LongToTimestampTypeHandler;
import lombok.Data;

/**
 * @Author: Lynn on 2024/11/26
 */
@Data
@TableName("line_message")
public class LineMessageVo {

  private Long lineUniqNum;

  private Long msgDtNum;

  @TableField(typeHandler = JsonbTypeHandler.class)
  private String message;

  private String quoteToken;

  private String messageId;

  private Long acNum;

  @TableField(typeHandler = LongToTimestampTypeHandler.class)
  private Long createTimestamp;

  /** ---join--- */

  private LineAccountVo lineAccountVo;
}
