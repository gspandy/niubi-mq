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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niubimq.pojo.Consumer;
import com.niubimq.pojo.JsonResult;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.thread.OriginalMsgProcessService;

/**
 * @Description: 消息接受（单条）
 * @author junjin4838
 * @version 1.0
 */

@Controller
@RequestMapping("/mqService")
public class MessageReceiverController {

	private static final Logger log = LoggerFactory.getLogger(MessageReceiverController.class);
	
	@Autowired
	private OriginalMsgProcessService originalMsgProcessService;
	
	private LinkedBlockingQueue<Message> originalMessageQueue;

	/**
	 * 消息发送
	 * @param producerSign  生产者标示
	 * @param businessSign  消费者标示
	 * @param messageContent 消息体
	 * @param planTime  如果该值合法，且大于当前时间，则为定时消息否则为即时消息
	 * @param allowRetryTimes 重试次数
	 * @return
	 */
	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendMsg(
			@RequestParam(value = "producerSign") String producerSign,
			@RequestParam(value = "businessSign") String businessSign,
			@RequestParam(value = "messageContent") String messageContent,
			@RequestParam(value = "planTime") String planTime,
			@RequestParam(value = "allowRetryTimes") String allowRetryTimes) {
		
		JsonResult result = new JsonResult();
		
		Message msg = new Message();
		
		//初始化队列
		init();
		
		//参数校验
		if(StringUtils.isEmpty(producerSign)){
			result.setErrMsg("生产者标识不能为空");
			result.setStatus(1);
		}else{
			msg.setProducerSign(producerSign);
		}
		
		if(StringUtils.isEmpty(messageContent)){
			result.setErrMsg("消息内容不能为空");
			result.setStatus(1);
		}else{
			msg.setMessageContent(messageContent);
		}
		
		if(StringUtils.isEmpty(allowRetryTimes)){
			result.setErrMsg("重试次数不能为空");
			result.setStatus(1);
		}else{
			msg.setAllowRetryTimes(Integer.parseInt(allowRetryTimes));
		}
		
		if(StringUtils.isEmpty(planTime)){
			//广播类型
			msg.setMessageType(2);
		}else{
			msg.setMessageType(1);
			msg.setPlanTime(Integer.valueOf(planTime));
		}
		
		//判断消费者是否存在
		@SuppressWarnings("static-access")
		Consumer consumer = originalMsgProcessService.consumerMap.get(businessSign);
		
		if(consumer == null){
			result.setErrMsg("指定的消费者不存在");
			result.setStatus(1);
			return result;
		}else{
			msg.setBusinessSign(businessSign);
			originalMessageQueue.add(msg);
			log.info("添加队列成功: "+ originalMessageQueue.size());
			result.setErrMsg("");
			result.setStatus(0);
			return result;
		}
	}
	
	/**
	 * 初始化队列
	 */
	@SuppressWarnings({ "unchecked" })
	private void init(){
		QueueFactory queueFactory  = QueueFactory.getInstance();
		originalMessageQueue = (LinkedBlockingQueue<Message>) queueFactory.getQueue(QueueFactory.ORIGIN_QUEUE);
	}

}
