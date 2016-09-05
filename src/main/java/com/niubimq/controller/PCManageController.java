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

package com.niubimq.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 生产者消费者管理
 * @author junjin4838
 * @version 1.0
 */

@Controller
@RequestMapping("/mq-service/pcManage/")
public class PCManageController {
	
	/**
	 * 查询生产者
	 * @param  producerSign 生产者表示
	 * @return HashMap
	 */
	@RequestMapping(value = "showProducer", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> showProducer(String producerSign){
		
		return null;
	}
	
	

}
