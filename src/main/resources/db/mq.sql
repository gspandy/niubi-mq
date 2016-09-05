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