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

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: Thread4 -- 读取时间燃尽的定时消息，放入到即时消息队列中
 *               Step1: 锁定消息
 *               Step2: 从DB读取相关数据
 *               Step3: 进入即时消费队列
 *               Step4: 从DB中删除
 * @author junjin4838
 * @version 1.0
 */
@Service
public class TimmingMsgConsumeService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(TimmingMsgConsumeService.class);
	
	/**
	 * 即时消息队列的引用
	 */
	private LinkedBlockingQueue<Message> immediatelyMessageQueue;
	
    /**
     * 休眠时间
     */
	private int sleepTime = 2000;
	
	/**
	 * 锁字段
	 */
	private String dataLock;
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();

	public void run() {
		
		while(this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING){
			
			//锁定消息数据
			msgPushService.lockTimmingMsg(dataLock);
			List<Message> timmingMsgList = msgPushService.getTimmingMsg(dataLock);
			
			// 放入即时消息队列，等待消费
            immediatelyMessageQueue.addAll(timmingMsgList);
            
            // 从DB中删除
            msgPushService.delTimmingMsg(dataLock);
            
            try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initInternal() {
		
		//队列的初始化
		QueueFactory queryFactory = QueueFactory.getInstance();
		immediatelyMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.IMMEDIATE_QUEUE);
		
		Integer spt = Integer.parseInt((String)reader.get("thread.TimmingMsgConsumeService.sleeptime"));
		this.sleepTime = spt;
		
		Map<String,String> map = System.getenv(); 
        dataLock = map.get("COMPUTERNAME") + "/" + map.get("USERDOMAIN") + "/" + map.get("USERNAME");
        log.info("-------TimmingMsgConsumeService LockFile-----" + dataLock);
        
        log.info("-------TimmingMsgConsumeService初始化完毕-----");
		
	}

	@Override
	public void startInternal() {
		
		Thread t = new Thread(this);
        t.setDaemon(true);
        t.setName("Timming-Msg-Consume-Service-Thread");
        t.start();

        log.info("-------TimmingMsgConsumeService启动完毕-----");
		
	}

	@Override
	public void destroyInternal() {
		log.info("-------TimmingMsgConsumeService销毁完毕-----");
	}
	

}
