package com.niubimq.thread;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
public class ImmediatelyMsgConsumeService extends BaseService {
	
	private final static Logger log = LoggerFactory.getLogger(ImmediatelyMsgConsumeService.class);
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();
	
	/**
	 * 即时消息队列
	 */
    private LinkedBlockingQueue<Message> immediateMessageQueue;
    
    private int sleepTime = 100;
	
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化
	 */
	@Override
	public void initInternal() {
		
		//队列初始化
		QueueFactory queryFactory = QueueFactory.getInstance();
		immediateMessageQueue = (LinkedBlockingQueue<Message>)queryFactory.getQueue(QueueFactory.IMMEDIATE_QUEUE);
		
		// 初始化休眠时间
		Integer spt = (Integer) reader.get("thread.ImmediatelyMsgConsumeService.sleeptime");
		this.sleepTime = spt;
		
		
		
		
		

	}

	@Override
	public void startInternal() {
		
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.setName("Immediately-Msg-Consume-Service-Thread");
		t.start();
		
		log.info("----ImmediatelyMsgConsumeService-- 启动完毕");

	}

	@Override
	public void destroyInternal() {
		// TODO Auto-generated method stub

	}

}
