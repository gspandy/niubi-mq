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
 * @Description: 定时消息入库
 * @author junjin4838
 * @version 1.0
 */
public class TimmingMsgSaveService extends BaseService {
	
	private static final Logger log = LoggerFactory.getLogger(TimmingMsgSaveService.class);
	
	/**
	 * 定时消息队列的引用
	 */
	private LinkedBlockingQueue<Message> timmingMessageQueue;
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();
	
	 /**
     * 休眠时间
     */
	private int sleepTime = 2000;

	public void run() {
		
		while(this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING){
			//批量入库
			List<Message> msgList = new ArrayList<Message>();
			
			for(int i=0;i<100 && timmingMessageQueue.size() !=0 ;i++){
				msgList.add(timmingMessageQueue.poll());
			}
			
			msgPushDao.saveTimmingMsg(msgList);
			
			//休息
			if(timmingMessageQueue.size() <= 100){
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
		//队列的初始化
	    QueueFactory queryFactory = QueueFactory.getInstance();
	    timmingMessageQueue = (LinkedBlockingQueue<Message>) queryFactory.getQueue(QueueFactory.TIMMING_QUEUE);
	    
	    Integer spt = (Integer) reader.get("thread.TimmingMsgConsumeService.sleeptime");
		this.sleepTime = spt;
		
		log.info("-----TimmingMsgSaveService初始化完毕----");

	}


	@Override
	public void startInternal() {

		Thread t = new Thread(this);
        t.setDaemon(true);
        t.setName("Timming-Msg-Save-Service-Thread");
        t.start();

        log.info("-------TimmingMsgSaveService启动完毕-----");
        
	}

	@Override
	public void destroyInternal() {
		
		while(timmingMessageQueue.size() != 0){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		log.info("-------TimmingMsgSaveService销毁完毕-----");

	}

}
