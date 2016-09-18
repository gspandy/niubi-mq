/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.niubimq.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.niubimq.client.MqClient;
import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Consumer;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.service.MsgReceiveService;
import com.niubimq.util.MessageBackup;
import com.niubimq.util.PropertiesReader;

/**
 * @Description Thread5--定时消息入库
 * @author junjin4838
 * @version 1.0
 */
@Service
public class OriginalMsgProcessService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(OriginalMsgProcessService.class);
	

	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();

	/**
	 * 原始消息队列的引用
	 */
	private LinkedBlockingQueue<Message> originalMessageQueue;
	
	/**
	 * 定时消息队列的引用
	 */
	private LinkedBlockingQueue<Message> timmingMessageQueue;

	/**
	 * 生产者消费者映射
	 */
	private static HashMap<String, String[]> pcMap;

	/**
	 * 所有消费者
	 */
	public static HashMap<String, Consumer> consumerMap;

	/**
	 * 休眠时间
	 */
	public int sleepTime = 500;

	public void run() {

		while (this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING) {
			
			Message msg = originalMessageQueue.poll();

			if (msg != null) {
				
				List<Message> p2pMsgList = fillMessageInfo(msg);
				
				//即时信息
				if(msg.getPlanTime() == null){
					//使用消息中间件
					try{
						MqClient.pushAllMsgQueue(p2pMsgList);
					}catch(Exception e){
						log.error(e.getMessage());
						//将消息备份
						MessageBackup.msgList2File(p2pMsgList);
					}
				}else{
					//定时消息
					timmingMessageQueue.addAll(p2pMsgList);
				}
			}else{
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * step1: 填充消费地址，重试次数等信息 
	 * step2: 广播 --> 点对点信息
	 * 
	 * @param msg
	 * @return
	 */
	private List<Message> fillMessageInfo(Message msg) {

		// 广播消息转成点对点消息
		List<Message> p2pMsgList = broadcast2PeerMsg(pcMap, msg);

		// 填充消费者信息
		p2pMsgList = setConsumerInfo(consumerMap, p2pMsgList);

		return p2pMsgList;

	}

	/**
	 * 填充消费地址 + 重试次数 + 相应时间等信息
	 * @param consumerMap
	 * @param p2pMsgList
	 * @return
	 */
	private List<Message> setConsumerInfo(HashMap<String, Consumer> consumerMap, List<Message> p2pMsgList) {

		for (Message msg : p2pMsgList) {

			//设置消费者的回调地址，该数据由数据库消费者指定，而不是生产者
			Consumer consumer = consumerMap.get(msg.getBusinessSign());
			msg.setResponseTimeOut(consumer.getResponseTimeOut());
			msg.setCallBackUrl(consumer.getCallBackUrl());
			msg.setRequestType(consumer.getRequestType());
			msg.setAllowRetryTimes(consumer.getAllowRetryTimes());
		}
		
		return p2pMsgList;
	}

	/**
	 * 广播转点对点信息
	 * 
	 * @return
	 */
	public List<Message> broadcast2PeerMsg(HashMap<String, String[]> pcMap,Message msg) {

		List<Message> msgList = new ArrayList<Message>();

		// 如果有消费者信息，则是点对点信息，无需转换
		if (StringUtils.isEmpty(msg.getBusinessSign())) {
			msgList.add(msg);
		} else {
			String[] mapConsumers = pcMap.get(msg.getProducerSign());
			for (String consumer : mapConsumers) {
				try {
					Message newMsg = msg.clone();
					newMsg.setBusinessSign(consumer);
					msgList.add(newMsg);
				} catch (CloneNotSupportedException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		return msgList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initInternal() {

		// 队列的初始化
		QueueFactory queryFactory = QueueFactory.getInstance();
		originalMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.ORIGIN_QUEUE);
		timmingMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.TIMMING_QUEUE);

		// 休眠时间
		Integer spt = Integer.parseInt((String)reader.get("thread.OriginalMsgProcessService.sleeptime"));
		sleepTime = spt;

		// 初始化P-C map数据
		initPCMapData();

		log.info("----OriginalMsgProcessService 初始化完毕-----");

	}

	@Override
	public void startInternal() {

		Thread t = new Thread(this);
		t.setDaemon(true);
		t.setName("Original-Msg-Process-Service-Thread");
		t.start();

		log.info("-------OriginalMsgProcessService 启动完毕-----");

	}

	@Override
	public void destroyInternal() {

		while (originalMessageQueue.size() != 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}

		log.info("-------OriginalMsgProcessService销毁完毕-----");

	}

	/**
	 * 初始化P-C
	 * 
	 * pcMap (key:生产者标识，value:生产者标识的数据) 
	 * 
	 * consumerMap (key:消费者标识，value:消费者对象)
	 * 
	 * 
	 */
	public void initPCMapData() {

        //从servletContext中获取相应的bean文件
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
		MsgReceiveService msgReceiveService = (MsgReceiveService) webApplicationContext.getBean("msgReceiveService");

		//生产者 - 消费者的对应关系
		pcMap = msgReceiveService.getPCMapList();
		
		System.out.println(pcMap);

		//获得所有消费者信息
		consumerMap = msgReceiveService.getConsumers();

	}

}
