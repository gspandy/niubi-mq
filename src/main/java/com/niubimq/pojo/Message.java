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

package com.niubimq.pojo;
/**
 * @Description: 消息体
 * @author junjin4838
 * @date 2016年8月31日
 * @version 1.0
 */
public class Message implements Cloneable{
	
	/**
	 * 请求方式
	 */
	public static final int REQUEST_TYPE_HTTP_GET = 1;
	public static final int REQUEST_TYPE_HTTP_POST = 2;
	
	/**
	 * 消费结果
	 */
	public static final int COSUME_RESULT_SUCCESS =1;
	public static final int COSUME_RESULT_FAILED = 2;
	
	/**
	 * 消息种类
	 */
	public static final int MESSAGE_TYPE_PEER2PEER = 1;
	public static final int MESSAGE_TYPE_BROADCAST = 2;
	
	/**
	 * 生产者标识
	 */
	private String producerSign;
	
	/**
	 * 消费者标识
	 */
	private String businessSign;
	
	/**
	 * 消费者回调
	 */
	private String callBackUrl;
	
	/**
	 * 允许重试次数
	 */
	private Integer allowRetryTimes;
	
	/**
	 * 消息类型：1-点对点类型 2-广播类型
	 */
	private Integer MessageType;
	
	/**
	 * 消息消费的请求类型 get-post
	 */
	private Integer requestType;
	
	/**
	 * 计划执行的时间
	 */
	private Integer planTime;
	
	/**
	 * 相应超时时间(单位为ms)
	 */
	private Integer responseTimeOut;
	
	/**
	 * 消息体
	 */
	private String messageContent;
	
	/**
	 * 重试次数
	 */
	private Integer retryTimes;
	
	/**
	 * 消费结果：1-成功 2-失败
	 */
	private Integer consumeResult;
	
	/**
	 * 消费失败的原因
	 */
	private String failedReason;
	
	/**
	 * 最后一次的相应时间
	 */
	private String finishTime;

	public String getProducerSign() {
		return producerSign;
	}

	public void setProducerSign(String producerSign) {
		this.producerSign = producerSign;
	}

	public String getBusinessSign() {
		return businessSign;
	}

	public void setBusinessSign(String businessSign) {
		this.businessSign = businessSign;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public Integer getAllowRetryTimes() {
		return allowRetryTimes;
	}

	public void setAllowRetryTimes(Integer allowRetryTimes) {
		this.allowRetryTimes = allowRetryTimes;
	}

	public Integer getMessageType() {
		return MessageType;
	}

	public void setMessageType(Integer messageType) {
		MessageType = messageType;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public Integer getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Integer planTime) {
		this.planTime = planTime;
	}

	public Integer getResponseTimeOut() {
		return responseTimeOut;
	}

	public void setResponseTimeOut(Integer responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Integer getConsumeResult() {
		return consumeResult;
	}

	public void setConsumeResult(Integer consumeResult) {
		this.consumeResult = consumeResult;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	

	@Override
	public Message clone() throws CloneNotSupportedException {
		return (Message)super.clone();
	}	
	
	@Override
	public String toString() {
		return "producerSign: "+ producerSign + "businessSign: " + businessSign;
	}

}
