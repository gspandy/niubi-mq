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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niubimq.pojo.Consumer;
import com.niubimq.pojo.JsonResult;
import com.niubimq.pojo.Producer;
import com.niubimq.service.ConsumerManageService;
import com.niubimq.service.ProducerManageService;
import com.niubimq.thread.OriginalMsgProcessService;

/**
 * @Description: 生产者和消费者的增删改查
 * @author junjin4838
 * @version 1.0
 */
@Controller
@RequestMapping("/mqService")
public class PCManageController {

	private static final Logger log = LoggerFactory.getLogger(PCManageController.class);
	
	@Autowired
	OriginalMsgProcessService originalMsgProcessService;

	@Autowired
	ProducerManageService producerManageService;

	@Autowired
	ConsumerManageService consumerManageService;

	/**
	 * 查询所有生产者
	 */
	@RequestMapping(value = "showProducer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult showProducer(@RequestParam(value = "producerSign") String producerSign) {

		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isEmpty(producerSign)) {
			jsonResult.setStatus(1);
			jsonResult.setErrMsg("生产者标识不能为空");
		}

		try {
			List<Producer> producerList = producerManageService.showProducer(producerSign);
			jsonResult.setStatus(0);
			jsonResult.setData(producerList);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonResult.setErrMsg("查询失败");
			jsonResult.setStatus(1);
		}

		return jsonResult;

	}

	/**
	 * 添加生产者
	 * @param producerSign :生产者标识
	 * @param description  :生产者描述
	 */
	@RequestMapping(value = "addProducer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addProducer(Producer producer) {

		JsonResult jsonResult = new JsonResult();

		// 参数校验
		String errMsg = "";
		errMsg = producerManageService.validateProducerParams(producer);

		if ("".equals(errMsg)) {
			try {
				producerManageService.addProducer(producer);
				jsonResult.setStatus(0);
				jsonResult.setData("添加生产者成功");
				originalMsgProcessService.initPCMapData();
			} catch (Exception e) {
				log.error(e.getMessage());
				jsonResult.setStatus(1);
				jsonResult.setData("添加生产者失败");
			}
		} else {
			jsonResult.setStatus(1);
			jsonResult.setErrMsg(errMsg);
		}

		return jsonResult;

	}

	/**
	 * 删除生产者
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteProducer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteProducer(Producer producer) {

		JsonResult jsonResult = new JsonResult();

		// 参数校验
		String errMsg = "";
		errMsg = producerManageService.validateProducerParams(producer);

		if ("".equals(errMsg)) {
			try {
				producerManageService.deleteProducer(producer);
				jsonResult.setStatus(0);
				jsonResult.setData("删除生产者成功");
				originalMsgProcessService.initPCMapData();
			} catch (Exception e) {
				log.error(e.getMessage());
				jsonResult.setStatus(1);
				jsonResult.setData("删除生产者失败");
			}
		} else {
			jsonResult.setStatus(1);
			jsonResult.setErrMsg(errMsg);
		}

		return jsonResult;
	}

	/**
	 * 查询所有消费者
	 * 
	 * @return
	 */
	@RequestMapping(value = "showConsumer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult showConsumer() {

		JsonResult jsonResult = new JsonResult();

		try {
			List<Consumer> consumer = consumerManageService.showConsumer();
			jsonResult.setStatus(0);
			jsonResult.setData(consumer);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonResult.setStatus(1);
			jsonResult.setErrMsg("查询消费者失败");
		}

		return jsonResult;
	}

	/**
	 * 添加消费者
	 * 
	 * @return
	 */
	@RequestMapping(value = "addConsumer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addConsumer(Consumer consumer) {

		JsonResult jsonResult = new JsonResult();

		// 参数校验
		String errMsg = "";
		errMsg = consumerManageService.validateConsumerParams(consumer);
		
		if("".equals(errMsg)){
		   try{
			 consumerManageService.addConsumer(consumer);
			 jsonResult.setStatus(0);
			 jsonResult.setData("添加消费者成功");
			 originalMsgProcessService.initPCMapData();
		   }catch(Exception e){
			 log.error(e.getMessage());
			 jsonResult.setStatus(1);
			 jsonResult.setData("添加消费者失败");
		   }
		}else{
		   jsonResult.setStatus(1);
		   jsonResult.setErrMsg(errMsg);
		}
		return jsonResult;
	}

	/**
	 * 删除消费者
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteConsumer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteConsumer(Consumer consumer) {
		
		JsonResult jsonResult = new JsonResult();

		// 参数校验
		@SuppressWarnings("unused")
		String errMsg = "";
		errMsg = consumerManageService.validateConsumerParams(consumer);
		
		try{
			consumerManageService.deleteConsumer(consumer);
			jsonResult.setStatus(0);
			jsonResult.setData("删除消费者成功");
			originalMsgProcessService.initPCMapData();
		}catch(Exception e){
			log.error(e.getMessage());
			jsonResult.setStatus(1);
			jsonResult.setData("删除消费者失败");
		}
		
		return jsonResult;
	}

	/**
	 * 更新消费者
	 */
	@RequestMapping(value = "updateConsumer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateConsumer(Consumer consumer) {
		
		JsonResult jsonResult = new JsonResult();

		// 参数校验
		String errMsg = "";
		errMsg = consumerManageService.validateConsumerParams(consumer);
		
		if("".equals(errMsg)){
			try{
				consumerManageService.updateConsumer(consumer);
				jsonResult.setStatus(0);
				jsonResult.setData("更新消费者成功");
				originalMsgProcessService.initPCMapData();
			}catch(Exception e){
				log.error(e.getMessage());
				jsonResult.setStatus(1);
				jsonResult.setData("更新消费者失败");
			}
		}else{
			jsonResult.setStatus(1);
			jsonResult.setErrMsg(errMsg);
		}
		return jsonResult;
	}

}
