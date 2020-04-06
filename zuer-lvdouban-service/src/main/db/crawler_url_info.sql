CREATE TABLE CRAWLER_URL_INFO
(
  id varchar2(36) PRIMARY KEY NOT NULL,
  ALT_DATE DATE,
  CRT_DATE DATE,
  URL VARCHAR2(500),
  URL_NAME VARCHAR2(200),
  TYPE varchar2(100),
  TYPE_NAME VARCHAR2(100)
);
COMMENT ON TABLE CRAWLER_URL_INFO IS '爬虫地址字典表';

ALTER TABLE CRAWLER_URL_INFO ADD UPD_USER VARCHAR2(50) NULL;
ALTER TABLE CRAWLER_URL_INFO ADD CRT_USER VARCHAR2(50) NULL;


ALTER TABLE CRAWLER_URL_INFO ADD UPD_NAME VARCHAR2(50) NULL;
ALTER TABLE CRAWLER_URL_INFO ADD CRT_NAME VARCHAR2(50) NULL;
ALTER TABLE CRAWLER_URL_INFO RENAME COLUMN ALT_DATE TO UPD_TIME;
ALTER TABLE CRAWLER_URL_INFO RENAME COLUMN CRT_DATE TO CRT_TIME;