package com.niubimq.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.niubimq.thread.BaseService;
import com.niubimq.thread.OriginalMsgProcessService;
import com.niubimq.thread.TimmingMsgSaveService;
import com.niubimq.util.PropertiesReader;

/**
 * @Description: 服务器启动监听
 * @author junjin4838
 * @version 1.0
 */
public class ServiceStartListener implements ServletContextListener {
	
	/**
	 * Thread5:定时消息入库
	 */
	public static BaseService originalMsgProcessService = new OriginalMsgProcessService();
	
	/**
	 * Thread7:定时消息入库
	 */
	public static BaseService timmingMsgSaveService = new TimmingMsgSaveService();
	
	/**
	 * 配置文件读取类
	 */
	private PropertiesReader reader = PropertiesReader.getInstance();
	
	public boolean useMessagePushService = true;

	/**
	 * 服务销毁
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		originalMsgProcessService.destory();
        timmingMsgSaveService.destory();
        if(useMessagePushService) Bootstrap.destory();
		
	}

	/**
	 * 服务初始化
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		
		if(reader.get("service.msgReciveService.useMsgPushService") != null){
			useMessagePushService = (Boolean)reader.get("service.msgReciveService.useMsgPushService");
		}
		
		if(useMessagePushService) Bootstrap.init();
		timmingMsgSaveService.init();
		originalMsgProcessService.init();
		
		if(useMessagePushService) Bootstrap.start();
		timmingMsgSaveService.start();
		originalMsgProcessService.start();
		
	}
	
}
