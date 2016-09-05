package com.niubimq.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description: 服务器启动监听
 * @author junjin4838
 * @version 1.0
 */
public class ServiceStartListener implements ServletContextListener {

	/**
	 * 服务销毁
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("jbk是个大臭逼，噢哈哈哈哈哈");

	}

	/**
	 * 服务初始化
	 */
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("jbk是个大帅哥，噢哈哈哈哈哈");
	}

}
