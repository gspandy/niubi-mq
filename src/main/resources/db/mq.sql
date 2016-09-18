--生产者--
CREATE TABLE `t_mq_producer` (
  `iAutoID` int(11) NOT NULL AUTO_INCREMENT,
  `sProducerSign` varchar(50) NOT NULL DEFAULT '' COMMENT '生产者标示',
  `sDesc` varchar(100) NOT NULL DEFAULT '' COMMENT '生产者描述',
  `iCreateTime` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `iUpdateTime` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `iStatus` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态（0：无效、1：有效）',
  PRIMARY KEY (`iAutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8

--定时消息表--
CREATE TABLE `t_mq_message_timing` (
  `iAutoID` int(11) NOT NULL AUTO_INCREMENT,
  `sProducerSign` varchar(50) NOT NULL COMMENT '生产者标示',
  `sBusinessSign` varchar(50) NOT NULL COMMENT '消费者标示',
  `sMessageContent` varchar(5000) NOT NULL COMMENT '消息体',
  `iMessageType` tinyint(1) NOT NULL COMMENT '消息类型：1-点对点类型；2-广播类型',
  `iPlanTime` int(11) NOT NULL COMMENT '计划运行时间',
  `sdataLock` varchar(200) DEFAULT NULL COMMENT '数据锁',
  `iStatus` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用 1-可用；0-删除',
  PRIMARY KEY (`iAutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8


