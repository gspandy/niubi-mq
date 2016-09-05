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

package com.niubimq.dao;

import java.util.HashMap;

import com.niubimq.pojo.Consumer;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
public interface MsgReceiveServiceDao {
	
	/**
	 * 读取所有消费者
	 * @return HashMap<String,Consumer>
	 */
	public HashMap<String,Consumer> getConsumers();
	
	/**
	 * 读取所有订阅消息
	 * @return HashMap<String,String[]>
	 */
	public HashMap<String,String[]> getPCMapList();
	
	

}
