CREATE TABLE TEST_USER
(
    ID    BIGSERIAL,
    NAME  VARCHAR(40),
    PHONE VARCHAR(40),
    PRIMARY KEY (ID)
);


CREATE TABLE
    LINE_MESSAGE
(
    LINE_UNIQ_NUM BIGSERIAL NOT NULL,
    MSG_DT_NUM    BIGINT    NOT NULL,
    MESSAGE       JSONB,
    QUOTE_TOKEN   VARCHAR(512),
    MESSAGE_ID    VARCHAR(512),
    PRIMARY KEY (LINE_UNIQ_NUM)
);

CREATE TABLE
    LINE_ACCOUNT
(
    AC_NUM              BIGSERIAL           NOT NULL,
    AC_NAME             VARCHAR(40)         NOT NULL,
    CHANNEL_ID          VARCHAR(512)        NOT NULL,
    CHANNEL_SECRET      VARCHAR(256)        NOT NULL,
    AUTHORIZATION_TOKEN VARCHAR(512)        NOT NULL,
    IS_ACTIVE           CHAR(1) DEFAULT 'Y' NOT NULL,
    PRIMARY KEY (AC_NUM)
);