package com.lynn.message_demo.action.event;

import com.lynn.message_demo.dto.LineInDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LINE 事件處理抽象類 - 策略模式的策略介面
 *
 * <p>所有事件處理類都必須繼承此抽象類，並使用 {@code @Service(LineEventActionType.XXX)} 註冊。
 * Spring 會自動將所有實現類注入到 {@link LineEventActionExecutor} 的 Map 中。
 *
 * <p>實作範例：
 * <pre>
 * {@code @Service(LineEventActionType.FOLLOW)}
 * public class FollowEventAction extends LineEventAction {
 *     @Override
 *     public void execEventProcess(LineInDto lineInDto) {
 *         // 處理追蹤事件
 *     }
 * }
 * </pre>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineEventActionType
 * @see LineEventActionExecutor
 */
@Setter(onMethod_ = {@Autowired}) // Lombok: 為所有 setter 方法加上 @Autowired，實現依賴注入
public abstract class LineEventAction {

  /**
   * 執行事件處理邏輯
   *
   * @param lineInDto 包含 LINE 事件資訊的 DTO
   */
  public abstract void execEventProcess(LineInDto lineInDto);
}
