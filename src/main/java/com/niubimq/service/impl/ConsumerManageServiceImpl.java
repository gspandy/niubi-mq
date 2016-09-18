package com.niubimq.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niubimq.dao.ConsumerManageDao;
import com.niubimq.pojo.Consumer;
import com.niubimq.service.ConsumerManageService;

/**
 * @Description: 关于消费者操作
 * @author junjin4838
 * @version 1.0
 */
@Service
@Transactional
public class ConsumerManageServiceImpl implements ConsumerManageService {

	@Autowired
	ConsumerManageDao consumerManageDao;

	/**
	 * 增加消费者信息
	 */
	public void addConsumer(Consumer consumer) {
		consumerManageDao.deleteConsumer(consumer);
		consumerManageDao.insertConsumer(consumer);
	}

	/**
	 * 删除消费者信息
	 */
	public void deleteConsumer(Consumer c) {
		consumerManageDao.deleteConsumer(c);
	}

	/**
	 * 修改消费者信息
	 */
	public void updateConsumer(Consumer consumer) {
		consumerManageDao.updateConsumer(consumer);
	}

	/**
	 * 查询消费者信息
	 */
	public List<Consumer> showConsumer() {
		return consumerManageDao.selectConsumer();
	}

	/**
	 * 校验消费者内置参数
	 */
	public String validateConsumerParams(Consumer consumer) {
		
		String errMsg = "";

		if (StringUtils.isEmpty(consumer.getBusinessSign())) {
			errMsg = "消费者标识不能为空";
			return errMsg;
		} else if (StringUtils.isEmpty(consumer.getDescription())) {
			errMsg = "消费者描述不能为空";
			return errMsg;
		} else if(StringUtils.isEmpty(consumer.getCallBackUrl())){
			errMsg = "消费地址不能为空";
			return errMsg;
		} else if(null == consumer.getResponseTimeOut()){
			errMsg = "超时时间不能为空";
			return errMsg;
		}
		
		return errMsg;
	}
	
	

}
