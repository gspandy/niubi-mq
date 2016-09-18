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

import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niubimq.client.MqClient;
import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: Thread6 -- 从消息中间件中读取消息，并放入即时消息队列中
 * @author junjin4838
 * @version 1.0
 */
@Service
public class MQMiddlewareMsgReadService extends BaseService {
	
	private final static Logger log = LoggerFactory.getLogger(MQMiddlewareMsgReadService.class);
	
	@Autowired
	private MqClient mqClient;

	/**
	 * 即时消息队列的引用
	 * */
	private LinkedBlockingQueue<Message> immediatelyMessageQueue;
	
	 /**
     * 休眠时间
     */
	private int sleepTime = 500;
	
	/**
	 * 消息队列最大值
	 */
	private int queueMaxCount = 1000; 
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();
	

	public void run() {
		
		while(this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING){
			
			// 如果即时消息队列大小没有超过阀值，则从MQ中读取消息
			if(immediatelyMessageQueue.size() < queueMaxCount){
				
				// 从MQ中读取消息
                String messageStr = MqClient.receiveMessage();
                
                if(StringUtils.isNotEmpty(messageStr)){
                	
                	//创建Message对象
                	Message message = (Message) JSONObject.toBean(JSONObject.fromObject(messageStr), Message.class);
                	
                	//放入队列
                	immediatelyMessageQueue.add(message);
                	
                	System.out.println("immediatelyMessageQueue 即时消费队列的长度： "+ immediatelyMessageQueue.size());
                	
                }else{
                	//MQ中无消息，休眠状态
                	try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
                }
				
			}else{
				//队列大小已经把超过，休眠状态
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}

	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initInternal() {

		// 队列的初始化
		QueueFactory queryFactory = QueueFactory.getInstance();
		immediatelyMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.IMMEDIATE_QUEUE);

		// 初始化休眠时间
		Integer spt = Integer.parseInt(reader.get("thread.TimmingMsgConsumeService.sleeptime").toString());
		this.sleepTime = spt;
		
		// 初始化即时消息队列最大值
        Integer qc = Integer.parseInt((String)reader.get("queue.immediatelyMessageQueue.maxCount"));
        this.queueMaxCount = qc;
        
        log.info("-------MQMiddlewareMsgReadService初始化完毕-----");

	}

	@Override
	public void startInternal() {
		
		Thread t = new Thread(this);
        t.setDaemon(true);
        t.setName("MQMiddleware-Msg-Read-Service-Thread");
        t.start();
        
        log.info("-------MQMiddlewareMsgReadService启动完毕-----");

	}

	@Override
	public void destroyInternal() {
		log.info("-------MQMiddlewareMsgReadService销毁完毕-----");
	}

}
