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
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: Thread3 -- 将消费过的消息入库
 * @author junjin4838
 * @version 1.0
 */
public class ConsumedMsgSaveService extends BaseService {

	private final static Logger log = LoggerFactory
			.getLogger(ConsumedMsgSaveService.class);

	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();

	/**
	 * Queue4 : 消费完毕的消息队列
	 */
	private LinkedBlockingQueue<Message> consumedMessageQueue;

	private int sleepTime = 2000;

	public void run() {

		while (state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING) {

			// 批量入库
			List<Message> msgList = new ArrayList<Message>();
			for (int i = 0; i < 400 && consumedMessageQueue.size() != 0; i++) {
				msgList.add(consumedMessageQueue.poll());
			}
			
			msgPushDao.saveConsumedMsg(msgList);

			// 休眠
			if (consumedMessageQueue.size() <= 100) {
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
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initInternal() {

		// 初始化队列引用
		QueueFactory queryFactory = QueueFactory.getInstance();
		consumedMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.CONSUMED_QUEUE);

		// 初始化休眠时间
		Integer spt = (Integer) reader.get("thread.ConsumedMsgSaveService.sleeptime");
		this.sleepTime = spt;

		log.info("-------ConsumedMsgSaveService初始化完毕-----");

	}

	@Override
	public void startInternal() {

		Thread t = new Thread(this);
		t.setDaemon(true);
		t.setName("Consumed-Msg-Save-Service-Thread");
		t.start();

		log.info("-------ConsumedMsgSaveService启动完毕----");

	}

	@Override
	public void destroyInternal() {

		while (consumedMessageQueue.size() != 0) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}

		log.info("-------ConsumedMsgSaveService销毁完毕-----");
	}

}
