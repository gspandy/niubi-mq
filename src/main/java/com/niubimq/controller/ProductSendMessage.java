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

package com.niubimq.controller;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;

/**
 * @Description: 消息接受（单条）
 * @author junjin4838
 * @version 1.0
 */

@Controller
@RequestMapping("/mq-service/sendMessage/")
public class ProductSendMessage {
	
	private final static Logger log = LoggerFactory.getLogger(ProductSendMessage.class);
	
	private static QueueFactory queryFactory = QueueFactory.getInstance();
	
	/**
	 * 原始消息队列引用
	 */
	private LinkedBlockingQueue<Message> originalMessageQueue;
	
	

	/**
	 * 生产者发送消息给消息系统
	 * 
	 * @param producerSign 生产者标识
	 * @param businessSign 消费者标识
	 * @param msgContent 消息内容体
	 * @param planTime   为空的话，为定时消息，不为空的话，为即时消息
	 * @param allowRetryTimes  允许重试的次数
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
	public void sendMessage(
			@RequestParam(value = "producerSign", required = true) String producerSign,
			@RequestParam(value = "businessSign") String businessSign,
			@RequestParam(value = "msgContent",required = true) String msgContent,
			@RequestParam(value = "planTime") Integer planTime,
			@RequestParam(value = "allowRetryTimes",required = true) Integer allowRetryTimes) {
		
		Message msg = new Message();
		
		//封装消息体
		if(StringUtils.isEmpty(allowRetryTimes)){
			msg.setAllowRetryTimes(allowRetryTimes);
		}
		
		if(StringUtils.isEmpty(planTime)){
			//广播类型传送方式
			msg.setMessageType(Message.MESSAGE_TYPE_BROADCAST);
		}else{
			//点对点的传送方式
			msg.setMessageType(Message.MESSAGE_TYPE_PEER2PEER);
			msg.setPlanTime(planTime);
		}
		
		msg.setProducerSign(producerSign);
		msg.setBusinessSign(businessSign);
		msg.setMessageContent(msgContent);
		
		originalMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.ORIGIN_QUEUE);
		
		//消息加入原始队列
		originalMessageQueue.add(msg);
		log.info("消息添加成功"+msgContent);
	

	}

}
