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

package com.niubimq.dao.impl;


import java.util.List;
import java.util.Map;

import com.niubimq.dao.BaseDao;
import com.niubimq.dao.MsgReceiveServiceDao;
import com.niubimq.pojo.Consumer;

/**
 * @Description: 消息接受服务使用的Dao
 * @author junjin4838
 * @version 1.0
 *
 */
public class MsgReceiveServiceDaoImpl extends BaseDao implements MsgReceiveServiceDao {

	/**
	 * 读取所有的consumer
	 */
	public List<Consumer> getConsumers() {
		
		List<Consumer> consumerList = sqlSession.selectList("getConsumers");
		return consumerList;
		
	}

	/**
	 * 读取订阅消息
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getPCMapList() {
		
		List<Map> pcMapList = sqlSession.selectList("getPCMapList");
		return pcMapList;
		
	}

   

}
