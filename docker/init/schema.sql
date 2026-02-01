-- =============================================
-- Message Demo Schema for MySQL
-- =============================================

-- LINE 帳號表
CREATE TABLE IF NOT EXISTS line_account (
    ac_num              BIGINT          PRIMARY KEY AUTO_INCREMENT  COMMENT '帳號流水號',
    ac_name             VARCHAR(100)                                COMMENT '帳號名稱',
    channel_id          VARCHAR(50)     NOT NULL                    COMMENT 'LINE Channel ID',
    channel_secret      VARCHAR(100)    NOT NULL                    COMMENT 'LINE Channel Secret',
    authorization_token VARCHAR(255)                                COMMENT 'LINE Authorization Token',
    is_active           CHAR(1)         DEFAULT 'Y'                 COMMENT '是否啟用 (Y/N)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='LINE 帳號設定';

-- LINE 訊息表
CREATE TABLE IF NOT EXISTS line_message (
    line_uniq_num       BIGINT          PRIMARY KEY AUTO_INCREMENT  COMMENT '訊息流水號',
    msg_dt_num          BIGINT                                      COMMENT '訊息日期編號',
    message             JSON                                        COMMENT '訊息內容 (JSON)',
    quote_token         VARCHAR(255)                                COMMENT '引用 Token',
    message_id          VARCHAR(50)                                 COMMENT 'LINE Message ID',
    ac_num              BIGINT                                      COMMENT '帳號流水號 (FK)',
    create_timestamp    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '建立時間',

    INDEX idx_ac_num (ac_num),
    INDEX idx_message_id (message_id),
    CONSTRAINT fk_message_account FOREIGN KEY (ac_num) REFERENCES line_account(ac_num)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='LINE 訊息記錄';

-- 測試用戶表
CREATE TABLE IF NOT EXISTS test_user (
    id                  BIGINT          PRIMARY KEY AUTO_INCREMENT  COMMENT '用戶 ID',
    name                VARCHAR(50)                                 COMMENT '用戶名稱',
    age                 BIGINT                                      COMMENT '年齡'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='測試用戶';

-- =============================================
-- 初始測試資料 (可選)
-- =============================================
INSERT INTO test_user (name, age) VALUES ('Test User', 25);
