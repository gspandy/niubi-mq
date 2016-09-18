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
	
	/**
	 * 推送消息标识
	 */
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
			useMessagePushService = Boolean.parseBoolean(((String) reader.get("service.msgReciveService.useMsgPushService")));
		}
		
		if(useMessagePushService) Bootstrap.init();
			timmingMsgSaveService.init();
			originalMsgProcessService.init();
		
		if(useMessagePushService) Bootstrap.start();
			timmingMsgSaveService.start();
			originalMsgProcessService.start();
		
	}
	
}
