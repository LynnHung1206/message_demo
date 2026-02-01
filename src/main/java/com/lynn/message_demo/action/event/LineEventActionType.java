package com.lynn.message_demo.action.event;

/**
 * LINE 事件類型常量
 *
 * <p>這些常量作為 Spring Bean 的名稱，必須與對應 {@link LineEventAction} 實現類的
 * {@code @Service} 註解名稱一致。
 *
 * <p>使用範例：
 * <pre>
 * {@code @Service(LineEventActionType.MESSAGE)}
 * public class MessageEventAction extends LineEventAction { ... }
 * </pre>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineEventAction
 * @see LineEventActionExecutor
 */
public class LineEventActionType {

  /** 訊息事件 - 用戶發送訊息時觸發 */
  public static final String MESSAGE = "MESSAGE_EVENT";

  /** 追蹤事件 - 用戶加入好友或解除封鎖時觸發 */
  public static final String FOLLOW = "FOLLOW_EVENT";

  /** 取消追蹤事件 - 用戶封鎖帳號時觸發 */
  public static final String UNFOLLOW = "UNFOLLOW_EVENT";

  /** 回傳事件 - 用戶點擊 Postback 按鈕時觸發 */
  public static final String POSTBACK = "POSTBACK_EVENT";

  /** 加入事件 - Bot 被加入群組或聊天室時觸發 */
  public static final String JOIN = "JOIN_EVENT";

  /** 離開事件 - Bot 被踢出群組或聊天室時觸發 */
  public static final String LEAVE = "LEAVE_EVENT";

  /** 收回事件 - 用戶收回訊息時觸發 */
  public static final String UNSEND = "UNSEND_EVENT";

  /** 成員加入事件 - 新成員加入群組時觸發 */
  public static final String MEMBER_JOINED = "MEMBER_JOINED_EVENT";

  /** 成員離開事件 - 成員離開群組時觸發 */
  public static final String MEMBER_LEFT = "MEMBER_LEFT_EVENT";
}
