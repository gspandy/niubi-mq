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

package com.niubimq.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niubimq.dao.ProducerManageDao;
import com.niubimq.pojo.Producer;
import com.niubimq.service.ProducerManageService;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
@Service
@Transactional
public class ProducerManageServiceImpl implements ProducerManageService {
	
	@Autowired
	private ProducerManageDao producerManageDao;

	/**
	 * 查询生产者信息
	 */
	public List<Producer> showProducer(String producerSign) {
		 List<Producer> producerList = producerManageDao.selectProducer(producerSign);
		return producerList;
	}

	 /**
     * 新增生产者信息
     * */
	public void addProducer(Producer producer) {
		producerManageDao.deleteProducer(producer);
		producerManageDao.insertProducer(producer);
	}

	 /**
     * 删除生产者信息
     * */
	public void deleteProducer(Producer p) {
		producerManageDao.deleteProducer(p);
	}

	/**
     * 修改生产者信息
     * */
	public void updateProducer(Producer producer) {
		producerManageDao.updateProducer(producer);
	}
 
    /**
     * 校验生产者内置参数
     */
	public String validateProducerParams(Producer producer) {
		
		String errMsg = "";
		
		if(StringUtils.isEmpty(producer.getProducerSign())){
			errMsg = "生产者标识不能为空";
			return errMsg;
		}else if(StringUtils.isEmpty(producer.getDescription())){
			errMsg = "生产者需求不能为空";
			return errMsg;
		}
		
		return errMsg;
	}

}
