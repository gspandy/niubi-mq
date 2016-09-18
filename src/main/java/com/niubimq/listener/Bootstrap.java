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

import com.niubimq.thread.BaseService;
import com.niubimq.thread.ConsumedMsgSaveService;
import com.niubimq.thread.ConsumingMsgProcessService;
import com.niubimq.thread.ImmediatelyMsgConsumeService;
import com.niubimq.thread.MQMiddlewareMsgReadService;
import com.niubimq.thread.TimmingMsgConsumeService;

/**
 * @Description: 服务器启动类(方向消费服务)
 * @author junjin4838
 * @version 1.0
 */
public class Bootstrap {

	// Thread1
	public static BaseService immediatelyMsgConsumeService = new ImmediatelyMsgConsumeService();

	// Thread2
	public static BaseService comsumingMsgProcessService = new ConsumingMsgProcessService();

	// Thread3
	public static BaseService consumedMsgSaveService = new ConsumedMsgSaveService();

	// Thread4
	public static BaseService timmingMsgConsumeService = new TimmingMsgConsumeService();

	// Thread6
	public static BaseService mqMiddlewareMsgReadService = new MQMiddlewareMsgReadService();

	/**
	 * 初始化
	 */
	public static void init() {
		immediatelyMsgConsumeService.init();
		comsumingMsgProcessService.init();
		consumedMsgSaveService.init();
		timmingMsgConsumeService.init();
		mqMiddlewareMsgReadService.init();
	}

	/**
	 * 启动
	 */
	public static void start() {
		immediatelyMsgConsumeService.start();
		comsumingMsgProcessService.start();
		consumedMsgSaveService.start();
		timmingMsgConsumeService.start();
		mqMiddlewareMsgReadService.start();
	}

	/**
	 * 销毁
	 */
	public static void destory() {
		mqMiddlewareMsgReadService.destory();
		timmingMsgConsumeService.destory();
		immediatelyMsgConsumeService.destory();
		comsumingMsgProcessService.destory();
		consumedMsgSaveService.destory();
	}
}
