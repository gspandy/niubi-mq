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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niubimq.pojo.JsonResult;
import com.niubimq.service.MsgReceiveService;
import com.niubimq.service.PCMapManageService;
import com.niubimq.service.impl.MsgReceiveServiceImpl;

/**
 * @Description: 广播订阅信息维护
 * @author junjin4838
 * @version 1.0
 */

@Controller
@RequestMapping("/mqService")
public class PCMapManageController {
	
	private static final Logger log = LoggerFactory.getLogger(PCMapManageController.class);

	@Autowired
	PCMapManageService pcMapManageService;

	/**
	 * 查询生产者消费者订阅关系
	 * 
	 * @param producerSign 生产者表示
	 * @return HashMap
	 */
	@RequestMapping(value = "showSubscriber", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult showSubscriber(
			@RequestParam(value = "producerSign",required=false) String producerSign,
			@RequestParam(value = "businessSign",required=false) String businessSign) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isEmpty(producerSign) && StringUtils.isEmpty(businessSign)) {
			jsonResult.setErrMsg("生成者标识或者消费者标识不能为空");
			jsonResult.setStatus(1);
			return jsonResult;
		}

		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("producerSign", producerSign);
		paramsMap.put("businessSign", businessSign);
		
		try{
			List<Map<String, String>> list = pcMapManageService.showSubscriber(paramsMap);
			jsonResult.setStatus(0);
			jsonResult.setData(list);
		}catch(Exception e){
			jsonResult.setStatus(1);
			jsonResult.setErrMsg("查询发生错误");
		}

		return jsonResult;

	}

	/**
	 * 增加生产者消费者订阅关系
	 * 
	 * @param producerSign
	 *            生产者表示
	 * @return HashMap
	 */
	@RequestMapping(value = "addSubscriber", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addSubscriber(
			@RequestParam(value = "producerSign") String producerSign,
			@RequestParam(value = "businessSign") String businessSign) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isEmpty(producerSign) || StringUtils.isEmpty(businessSign)) {
			jsonResult.setErrMsg("生成者标识或者消费者标识不能为空");
			jsonResult.setStatus(1);
			return jsonResult;
		}
		
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("producerSign", producerSign);
		paramsMap.put("businessSign", businessSign);

		try{
			pcMapManageService.addSubscriber(paramsMap);
			jsonResult.setStatus(0);
			jsonResult.setData("增加生产者消费者订阅关系成功");
		}catch(Exception e){
			log.error(e.getMessage());
			jsonResult.setStatus(1);
			jsonResult.setData("增加生产者消费者订阅关系失败");
		}

		return jsonResult;
		
	}

	/**
	 * 删除生产者消费者订阅关系
	 * 
	 * @param producerSign
	 *            生产者表示
	 * @return HashMap
	 */
	@RequestMapping(value = "deleteSubscriber", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteSubscriber(
			@RequestParam(value = "producerSign",required=false) String producerSign,
			@RequestParam(value = "businessSign",required=false) String businessSign) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isEmpty(producerSign) && StringUtils.isEmpty(businessSign)) {
			jsonResult.setErrMsg("生成者标识或者消费者标识不能为空");
			jsonResult.setStatus(1);
			return jsonResult;
		}
		
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("producerSign", producerSign);
		paramsMap.put("businessSign", businessSign);

		try{
			pcMapManageService.deleteSubscriber(paramsMap);
			jsonResult.setStatus(0);
			jsonResult.setData("删除生产者消费者订阅关系成功");
		}catch(Exception e){
			log.error(e.getMessage());
			jsonResult.setStatus(1);
			jsonResult.setData("删除生产者消费者订阅关系失败");
		}

		return jsonResult;

	}
	
	@RequestMapping(value="test",method = RequestMethod.GET)
	public void test(){
		MsgReceiveService service = new MsgReceiveServiceImpl();
		service.getPCMapList();
		
	}

}
