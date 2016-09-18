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

import com.niubimq.dao.BaseDao;
import com.niubimq.dao.ProducerManageDao;
import com.niubimq.pojo.Producer;

/**
 * @Description: 生产者信息使用Dao
 * @author junjin4838
 * @version 1.0
 */
public class ProducerManageDaoImpl extends BaseDao implements ProducerManageDao {


	public List<Producer> selectProducer(String producerSign) {
		return sqlSession.selectList("selectProducer", producerSign);
	}

	public void insertProducer(Producer producer) {
		if(producer == null) return;
		sqlSession.insert("insertProducer", producer);
	}

	public void deleteProducer(Producer producer) {
		if(producer == null) return;
		sqlSession.delete("deleteProducer", producer);
	}

	public void updateProducer(Producer producer) {
		sqlSession.update("updateProducer",producer);
	}
}
