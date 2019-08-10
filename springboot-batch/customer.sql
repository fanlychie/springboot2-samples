CREATE TABLE `CUSTOMER` (
  `ID` char(32) NOT NULL COMMENT '主键',
  `NAME` varchar(16) NOT NULL COMMENT '姓名',
  `MOBILE` char(11) NOT NULL COMMENT '手机号码',
  `AGE` int(11) NOT NULL COMMENT '年龄',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;