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
import com.niubimq.dao.PCMapManageDao;

/**
 * @Description: 订阅信息管理 
 * @author junjin4838
 * @version 1.0
 */
public class PCManageDaoImpl extends BaseDao implements PCMapManageDao{

    /**
     * 展示生成者-消费者 映射关系
     */
	public List<Map<String, String>> selectPCMap(Map<String, String> pcMap) {
		return sqlSession.selectList("selectPCMap", pcMap);
	}


	/**
	 * 删除生成者-消费者 映射关系
	 */
	public void deletePCMap(Map<String, String> pcMap) {
		sqlSession.update("deletePCMap", pcMap);
	}


	/**
	 * 添加生产者-消费者 映射关系
	 */
	public void insertPCMap(Map<String, String> pcMap) {
		sqlSession.update("insertPCMap", pcMap);
	}

}
