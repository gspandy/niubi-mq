package com.niubimq.thread;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.pojo.MessageWrapper;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.MessageSender;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: thread1
 *    step1 :从即时消息队列中取出消息
 *    step2 :发送消息给指定的消费者
 *    step3 :将消费对象包装，放入待相应的消息队列 
 *    
 * @author junjin4838
 * @version 1.0
 */
@Service
public class ImmediatelyMsgConsumeService extends BaseService {
	
	private final static Logger log = LoggerFactory.getLogger(ImmediatelyMsgConsumeService.class);
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();
	
	/**
	 * 即时消息队列的引用 
	 */
    private LinkedBlockingQueue<Message> immediateMessageQueue;
    
    /**
     * 包含连接对象的消息队列的引用
     */
    private LinkedBlockingQueue<MessageWrapper> consumingMessageQueue;
    
    /**
     * Queue4: 消费完毕的消息队列
     */
    private LinkedBlockingQueue<Message> consumedMessageQueue;
    
    /**
     * 消费队列最大值
     */
    private int consumingMsgQueueMaCount = 1000;
    
    /**
     * 消息消费服务超时时间
     */
    private int msgResponseTimeout;
    
    /**
     * 休眠时间
     */
    private int sleepTime = 100;
	
	public void run() {
		
		while(this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING){
			
			Message msg = null;
			
			if(consumingMessageQueue.size() <= consumingMsgQueueMaCount){
				msg = immediateMessageQueue.poll();
			}
			
			if(msg != null){
				//设定超时时间
				if(msg.getResponseTimeOut() == null){
					msg.setResponseTimeOut(msgResponseTimeout);
				}
			
			//开始消费
			sleepTime = 0;
			
			try{
			  //推送消息给消费者
			  Socket so = MessageSender.sendMessageBySocket(msg);
			  if(so == null) throw new Exception("消息推送失败");
			  
			  MessageWrapper msgWrapper = new MessageWrapper();
			  msgWrapper.setMessage(msg);
			  msgWrapper.setConnCreateTime(System.currentTimeMillis());
			  msgWrapper.setSocket(so);
			  msgWrapper.setResponseTimeOut(msg.getResponseTimeOut());
				
			  consumingMessageQueue.add(msgWrapper);
				
			}catch(Exception e){
			   // 消费异常
			   if(msg.getAllowRetryTimes() > msg.getRetryTimes()){
				   //允许重试，将消息放入即时消费队列等待再次消费
				   msg.setRetryTimes(msg.getRetryTimes()+1);
				   msg.setConsumeResult(2);
				   msg.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				   msg.setFailedReason(e.getMessage());
				   immediateMessageQueue.add(msg);
			   }else{
				  // 重试结束
				  msg.setConsumeResult(2);
				  msg.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				  msg.setFailedReason(e.getMessage());
				  // 失败消息入库
				  consumedMessageQueue.add(msg);
			   }
			   
			   log.error("消息推送失败，消息内容："+JSONObject.fromObject(msg).toString() + "\n失败原因："+ e.getMessage());
			   e.printStackTrace();
				
			}
		  }else{
			  try {
				Thread.sleep((sleepTime++) * sleepTime < 2000 ? (sleepTime) * sleepTime : 2000);
			} catch (InterruptedException e) {
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
		
		//队列初始化
		QueueFactory queryFactory = QueueFactory.getInstance();
		immediateMessageQueue = (LinkedBlockingQueue<Message>)queryFactory.getQueue(QueueFactory.IMMEDIATE_QUEUE);
		consumingMessageQueue = (LinkedBlockingQueue<MessageWrapper>)queryFactory.getQueue(QueueFactory.CONSUMING_QUEUE);
		
		// 初始化休眠时间
		Integer spt = Integer.parseInt((String)reader.get("thread.ImmediatelyMsgConsumeService.sleeptime"));
		this.sleepTime = spt;
		
		// 初始化全局响应超时时间
        Integer mrt = Integer.parseInt(reader.get("message.responseTimeOut").toString());
        if(mrt != null){
            this.msgResponseTimeout = mrt;
        }
        
        // 初始化consumingMsgQueueMaxCount
        Integer cmmt = Integer.parseInt(reader.get("queue.consumingMessageQueue.maxCount").toString());
        if(mrt != null){
            this.consumingMsgQueueMaCount = cmmt;
        }
        
        log.info("-------ImmediatelyMsgConsumeService初始化完毕-----");
		
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
		
		while(immediateMessageQueue.size() != 0){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		log.info("-------ImmediatelyMsgConsumeService销毁完毕-----");

	}

}
