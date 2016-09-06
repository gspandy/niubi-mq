package com.niubimq.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.niubimq.listener.LifeCycle;
import com.niubimq.pojo.Message;
import com.niubimq.pojo.MessageWrapper;
import com.niubimq.queue.QueueFactory;
import com.niubimq.util.MessageSender;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: Thread2 : 等待消费者响应消息结果，并且处理结果
 * @author junjin4838
 * @version 1.0
 */
public class ConsumingMsgProcessService extends BaseService{
	
	private final static Logger log = LoggerFactory.getLogger(ImmediatelyMsgConsumeService.class);
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();

    /**
     * Queue1 : 即时消息队列的引用
     */
	private LinkedBlockingQueue<Message> immediatelyMessageQueue;
	
	/**
	 * Queue2 : 包含连接对象的消息队列引用
	 */
	private LinkedBlockingQueue<MessageWrapper> consumingMessageQueue;
	
	/**
	 * Queue4 : 已经消费完毕的消息队列
	 */
	private LinkedBlockingQueue<Message>  consumedMessageQueue;
	
	/**
	 * 休眠时间
	 */
	private Integer sleepTimes = 100;
	
	public void run() {
		
		
		while(this.state == LifeCycle.RUNNING || this.state == LifeCycle.STARTING){
			
			MessageWrapper msgWrapper = consumingMessageQueue.poll();
			
			//队列不为空时，开始处理响应
			sleepTimes = 0;
			if(msgWrapper != null){
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(msgWrapper.getSocket().getInputStream()));
					if(in.ready()){
						//解析响应数据
						Map<String,String> responseMap = MessageSender.parseResonse(in);
						//关闭连接
						msgWrapper.getSocket().close();
						
						//消费成功
						if(StringUtils.endsWith("true", responseMap.get("success"))){
							consumeSuccess(msgWrapper);
							log.info("消费成功，消息进入消息队列，等待进去DB");
						}else{
					        throw new Exception(responseMap.get("failedReason"));
						}
					}else{
						//如果没有响应数据
						if(msgWrapper.getConnCreateTime() + msgWrapper.getResponseTimeOut()*1000 > System.currentTimeMillis()){
							//消费失败
							throw new Exception("消费超时");
						}else{
							//继续等待消费者响应
							consumingMessageQueue.add(msgWrapper);
						}
					}
				} catch (Exception e) {
					log.error("消息响应处理失败，消息内容: "+ JSONObject.fromObject(msgWrapper.getMessage()).toString() + "\n失败原因：" + e.getMessage());
                    e.printStackTrace();
                    
                    if(msgWrapper.getMessage().getRetryTimes() < msgWrapper.getMessage().getAllowRetryTimes()){
                    	//准备下一次重试，消息进入即时消费队列
                    	retryNext(msgWrapper, e.getMessage());
                    }else{
                    	//重试结束，消息进入已消费队列
                    	retryFinished(msgWrapper, e.getMessage());
                    }
				}
			}else{
				try {
					Thread.sleep((sleepTimes++) * sleepTimes < 5000 ? (sleepTimes) * sleepTimes : 5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 消费成功，消息进入消费队列，等待入DB
	 * @param msgWrapper
	 */
	private void consumeSuccess(MessageWrapper msgWrapper){
		msgWrapper.setConsumeResult(Message.COSUME_RESULT_SUCCESS);
		msgWrapper.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		consumedMessageQueue.add(msgWrapper.getMessage());
	}
	
	/**
     * 重试结束，消息进入已消费队列
     * */
    private void retryFinished(MessageWrapper msgWrapper, String failedReason) {
        // 重试结束
        msgWrapper.setConsumeResult(Message.COSUME_RESULT_FAILED);
        msgWrapper.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        msgWrapper.setFailedReason(failedReason);
        consumedMessageQueue.add(msgWrapper.getMessage());
    }
    
    /**
     * 准备下一次重试，消息进入即时消费队列
     * */
    private void retryNext(MessageWrapper msgWrapper, String failedReason) {
        // 重试
        msgWrapper.setRetryTimes(msgWrapper.getMessage().getRetryTimes() + 1);
        msgWrapper.setConsumeResult(Message.COSUME_RESULT_FAILED);
        msgWrapper.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        msgWrapper.setFailedReason(failedReason);
        immediatelyMessageQueue.add(msgWrapper.getMessage());
    }

	@SuppressWarnings("unchecked")
	@Override
	public void initInternal() {
		
		//队列初始化
	    QueueFactory queryFactory = QueueFactory.getInstance();
	    immediatelyMessageQueue = (LinkedBlockingQueue<Message>)queryFactory.getQueue(QueueFactory.IMMEDIATE_QUEUE);
	    consumingMessageQueue = (LinkedBlockingQueue<MessageWrapper>)queryFactory.getQueue(QueueFactory.CONSUMING_QUEUE);
	    consumedMessageQueue = (LinkedBlockingQueue<Message>)queryFactory.getQueue(QueueFactory.CONSUMED_QUEUE);
	    
	    // 初始化休眠时间
	 	Integer spt = (Integer) reader.get("thread.ConsumingMsgProcessService.sleeptime");
	 	this.sleepTimes = spt;
	 	
	 	log.info("-----------ConsumingMsgProcessService初始化完毕-------");
		
	}


	@Override
	public void startInternal() {
		
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.setName("Consuming-Msg-Process-Service-Thread");
		t.start();
		
		log.info("-----------ConsumingMsgProcessService启动完毕-------");
		
	}


	@Override
	public void destroyInternal() {
	   while(consumingMessageQueue.size() != 0){
	    try {
	       Thread.sleep(500);
	    } catch (InterruptedException e) {
	       log.error(e.getMessage());
	       e.printStackTrace();
	    }
	  }
       log.info("-------ComsumingMsgProcessService销毁完毕-----");
	}

}
