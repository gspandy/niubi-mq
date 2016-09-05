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

import java.util.List;

import com.niubimq.pojo.Producer;

/**
 * @author junjin4838
 * @date 2016年8月31日
 * @version 1.0
 */
public interface PCManageDao {
	
	/**
	 * 查询生产者信息
	 * @param producerSign
	 * @return List
	 */
	public List<Producer> showProducer(String producerSign);
	
	/**
	 * 新增生产者信息
	 * @param producer
	 */
	public void addProducer(Producer producer);
	
	/**
	 * 删除生产者信息
	 * @param producer
	 */
	public void deleteProducer(Producer producer);
	
	/**
	 * 修改生产者信息
	 * @param producer
	 */
	public void updateProducer(Producer producer); 


}
