package com.niubimq.client;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niubimq.pojo.Message;

/**
 * @Description: 消息中间件客户端 -- 生产者模式
 * @author junjin4838
 * @date 2016年9月1日
 * @version 1.0
 */

@Service
public class MessageProductorService {
	
	private final static Logger log = LoggerFactory.getLogger(MessageProductorService.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 发送消息给MQ(单挑发送)
	 * @param message
	 */
	public void pushOneMsgQueue(Message message){
		amqpTemplate.convertAndSend(JSONObject.fromObject(message).toString());
		log.info("成功插入消息"+message.toString());
		
	}
	
	/**
	 * 发送消息给MQ(批量发送)
	 * @param msgList
	 */
	public void pushAllMsgQueue(List<Message> msgList){
		
		if( null == msgList || msgList.size() == 0){
			return;
		}
		
		for (Message message : msgList) {
			amqpTemplate.convertAndSend(JSONObject.fromObject(message).toString());
			log.info("成功插入消息"+message.toString());
		}
		
	}

	/**
	 * 发送消息给MQ(单挑发送)
	 * @param routingKey 路由指定
	 * @param message    消息
	 */
	public void pushToMessageQueue(String routingKey, String message) {
		amqpTemplate.convertAndSend(routingKey, message);
		System.out.println("成功插入消息 " + message);
	}



}
