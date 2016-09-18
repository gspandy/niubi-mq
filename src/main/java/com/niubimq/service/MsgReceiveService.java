package com.niubimq.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.niubimq.pojo.Consumer;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
public interface MsgReceiveService {
	
	/**
	 * 读取所有消费者
	 * @return HashMap<String,Consumer>
	 */
	public HashMap<String,Consumer> getConsumers();
	
	/**
	 * 读取所有订阅消息
	 * @return HashMap<String,String[]>
	 */
	public HashMap<String,String[]> getPCMapList();

}
