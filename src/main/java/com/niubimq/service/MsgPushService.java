package com.niubimq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.niubimq.pojo.Message;

/**
 * @Description:
 * @author junjin4838
 * @version 1.0
 */
public interface MsgPushService {

	/**
	 * 保存消费过的数据（单条保存）
	 * */
	public void saveConsumedMsg(Message msg);

	/**
	 * 保存消费过的数据（批量保存）
	 * */
	public void saveConsumedMsg(List<Message> msgList);

	/**
	 * 保存定时消息数据（单条保存）
	 * */
	public void saveTimmingMsg(Message msg);

	/**
	 * 保存定时消息数据（批量保存）
	 * */
	public void saveTimmingMsg(List<Message> msgList);

	/**
	 * 获取定时消息数据
	 * */
	public List<Message> getTimmingMsg(String dataLock);

	/**
	 * 锁定消息数据
	 * */
	public void lockTimmingMsg(String dataLock);

	/**
	 * 删除定时消息数据
	 * */
	public void delTimmingMsg(String dataLock);

}
