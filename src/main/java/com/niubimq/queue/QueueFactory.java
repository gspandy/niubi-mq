package com.niubimq.queue;

import java.util.concurrent.LinkedBlockingQueue;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

import com.niubimq.pojo.Message;
import com.niubimq.pojo.MessageWrapper;

/**
 * @Description: </p>
 * @author junjin4838
 * @version 1.0
 */
public class QueueFactory implements DynamicMBean {
	
	private static Object lock = new Object();

	public static final int ORIGIN_QUEUE = 1;
	public static final int IMMEDIATE_QUEUE = 2;
	public static final int CONSUMING_QUEUE = 3;
	public static final int CONSUMED_QUEUE = 4;
	public static final int TIMMING_QUEUE = 5;

	private static QueueFactory queueFactoryInstance;

	/**
	 * Queue1:原始消息队列
	 */
	public static LinkedBlockingQueue<Message> originalMessageQueue = new LinkedBlockingQueue<Message>();

	/**
	 * Queue2:即时消息队列
	 */
	public static LinkedBlockingQueue<Message> immediatelyMessageQueue = new LinkedBlockingQueue<Message>();

	/**
	 * Queue3:包含连接对象的消息队列
	 */
	public static LinkedBlockingQueue<MessageWrapper> consumingMessageQueue = new LinkedBlockingQueue<MessageWrapper>();

	/**
	 * Queue4:消费完毕的消息队列
	 */
	public static LinkedBlockingQueue<Message> consumedMessageQueue = new LinkedBlockingQueue<Message>();

	/**
	 * Queue5:定时消息队列
	 */
	public static LinkedBlockingQueue<Message> timmingMessageQueue = new LinkedBlockingQueue<Message>();

	/**
	 * 默认构造器
	 */
	public QueueFactory() {

	}

	/**
	 * 双重检锁 -- 获取单例
	 * 
	 * @return
	 */
	public  static QueueFactory getInstance() {
		if (queueFactoryInstance == null) {
			synchronized (lock) {
				if (queueFactoryInstance == null) {
					queueFactoryInstance = new QueueFactory();
					return queueFactoryInstance;
				}
			}
		}
		return queueFactoryInstance;
	}

	/**
	 * 返回相应的消息队列
	 * 
	 * @param queueType
	 * @return
	 */
	public LinkedBlockingQueue<?> getQueue(int queueType) {

		if (queueType == ORIGIN_QUEUE) {
			return originalMessageQueue;

		} else if (queueType == IMMEDIATE_QUEUE) {
			return immediatelyMessageQueue;

		} else if (queueType == CONSUMING_QUEUE) {
			return consumingMessageQueue;

		} else if (queueType == CONSUMED_QUEUE) {
			return consumedMessageQueue;

		} else if (queueType == TIMMING_QUEUE) {
			return timmingMessageQueue;

		} else
			return null;

	}

	public Object getAttribute(String queueType)
			throws AttributeNotFoundException, MBeanException,
			ReflectionException {
		if (queueType.equals("originalMessageQueueSize")) {
			return originalMessageQueue.size();
		} else if (queueType.equals("immediatelyMessageQueueSize")) {
			return immediatelyMessageQueue.size();
		} else if (queueType.equals("consumingMessageQueueSize")) {
			return consumingMessageQueue.size();
		} else if (queueType.equals("consumedMessageQueueSize")) {
			return consumedMessageQueue.size();
		} else if (queueType.equals("timmingMessageQueueSize")) {
			return timmingMessageQueue.size();
		} else
			return null;
	}

	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException {

	}

	public AttributeList getAttributes(String[] attributes) {
		return null;
	}

	public AttributeList setAttributes(AttributeList attributes) {
		return null;
	}

	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		return null;
	}

	public MBeanInfo getMBeanInfo() {

		MBeanAttributeInfo[] dAttributes = new MBeanAttributeInfo[] {
				new MBeanAttributeInfo("originalMessageQueueSize", "Integer",
						"原始消息队列", true, false, false),
				new MBeanAttributeInfo("immediatelyMessageQueueSize",
						"Integer", "即时消息队列", true, false, false),
				new MBeanAttributeInfo("consumingMessageQueueSize", "Integer",
						"正在消费的消息队列", true, false, false),
				new MBeanAttributeInfo("consumedMessageQueueSize", "Integer",
						"已消费等待入库的消息队列", true, false, false),
				new MBeanAttributeInfo("timmingMessageQueueSize", "Integer",
						"已消费等待入库的消息队列", true, false, false) };
		MBeanInfo info = new MBeanInfo(this.getClass().getName(),
				"QueueFactory", dAttributes, null, null, null);
		return info;

	}

}
