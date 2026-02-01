package com.lynn.message_demo.action.message;

/**
 * LINE 訊息類型常量
 *
 * <p>這些常量作為 Spring Bean 的名稱，必須與對應 {@link LineMessageAction} 實現類的
 * {@code @Service} 註解名稱一致。
 *
 * <p>使用範例：
 * <pre>
 * {@code @Service(LineMessageActionType.TEXT)}
 * public class TextMessageAction extends LineMessageAction { ... }
 * </pre>
 *
 * @Author: Lynn on 2024/11/28
 * @see LineMessageAction
 * @see LineMessageActionExecutor
 */
public class LineMessageActionType {

  /** 文字訊息 */
  public static final String TEXT = "TEXT_MESSAGE";
  /** 貼圖訊息 */
  public static final String STICKER = "STICKER_MESSAGE";
  /** 圖片訊息 */
  public static final String IMAGE = "IMAGE_MESSAGE";
  /** 影片訊息 */
  public static final String VIDEO = "VIDEO_MESSAGE";
  /** 語音訊息 */
  public static final String AUDIO = "AUDIO_MESSAGE";
  /** 檔案訊息 */
  public static final String FILE = "FILE_MESSAGE";
  /** 位置訊息 */
  public static final String LOCATION = "LOCATION_MESSAGE";

}
