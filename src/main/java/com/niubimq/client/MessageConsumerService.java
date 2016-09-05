package com.niubimq.client;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
@Service
public class MessageConsumerService {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private Queue queue;
	
	
	/**
	 * 从消息队列中获取数据
	 */
	public String onMessage(){
		return (String)amqpTemplate.receiveAndConvert(queue.getName());
	}


}
