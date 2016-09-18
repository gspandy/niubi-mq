package com.niubimq.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: 定时消息入库
 * @author junjin4838
 * @version 1.0
 */
@Service
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
				//测试的时候，关注是否重复添加了数据 = ~ = 
				System.out.println("队列发出数据： " + timmingMessageQueue.poll());
				msgList.add(timmingMessageQueue.poll());
			}
			
			if(msgList.size() != 0){
				msgPushService.saveTimmingMsg(msgList);
			}
			
			//队列什么时候进入休眠，可以按照既定的业务去判别，这边没有做严格的控制，其实还是来一条数据就即时的保存一条数据到DB
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
	    
	    int spt = Integer.parseInt((String) reader.get("thread.TimmingMsgConsumeService.sleeptime"));
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
