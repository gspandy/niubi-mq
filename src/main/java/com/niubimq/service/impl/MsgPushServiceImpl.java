package com.niubimq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niubimq.dao.MsgPushServiceDao;
import com.niubimq.pojo.Message;
import com.niubimq.service.MsgPushService;

/**
 * @Description: 消息推送服务
 * @author junjin4838
 * @version 1.0
 */
@Service("msgPushService")
public class MsgPushServiceImpl implements MsgPushService {
	
	@Autowired
	private MsgPushServiceDao msgPushServiceDao;
	
	/**
	 * 保存消费过的数据（单条保存）
	 * */
	public void saveConsumedMsg(Message msg) {
		msgPushServiceDao.insertConsumedMsg(msg);
	}

	/**
	 * 保存消费过的数据（批量保存）
	 * */
	public void saveConsumedMsg(List<Message> msgList) {
		msgPushServiceDao.insertConsumedMsgBatch(msgList);
	}

	/**
	 * 保存定时消息数据（单条保存）
	 * */
	public void saveTimmingMsg(Message msg) {
		msgPushServiceDao.insertTimmingMsg(msg);
	}

	/**
	 * 保存定时消息数据（批量保存）
	 * */
	public void saveTimmingMsg(List<Message> msgList) {
		msgPushServiceDao.insertTimmingMsgBatch(msgList);
	}

	/**
	 * 获取定时消息数据
	 * */
	public List<Message> getTimmingMsg(String dataLock) {
		return msgPushServiceDao.selectTimmingMsg(dataLock);
	}

	/**
	 * 锁定消息数据
	 * */
	public void lockTimmingMsg(String dataLock) {
		msgPushServiceDao.lockTimmingMsg(dataLock);
	}

	/**
	 * 删除定时消息数据
	 * */
	public void delTimmingMsg(String dataLock) {
		msgPushServiceDao.delTimmingMsg(dataLock);
	}

}
