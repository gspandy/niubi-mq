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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niubimq.dao.PCMapManageDao;
import com.niubimq.service.PCMapManageService;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
@Service
@Transactional
public class PCMapManageServiceImpl implements PCMapManageService {
	
	@Autowired
	PCMapManageDao pcMapManageDao;

	public List<Map<String, String>> showSubscriber(Map<String, String> pcMap) {
		return pcMapManageDao.selectPCMap(pcMap);
	}

	public void deleteSubscriber(Map<String, String> pcMap) {
		pcMapManageDao.deletePCMap(pcMap);
	}

	public void addSubscriber(Map<String, String> pcMap) {
		pcMapManageDao.insertPCMap(pcMap);
	}

}
