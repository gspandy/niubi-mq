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

import java.net.Socket;
import java.net.URLConnection;

/**
 * @Description: 消息包装类
 * @author junjin4838
 * @version 1.0
 */
public class MessageWrapper {
	
	/**
	 * 被包装的消息
	 */
	private Message message;
	
	/**
	 * 消费者连接对象
	 */
	private URLConnection conn;
	
	/**
	 * 消费者socket对象
	 */
	private Socket socket;
	
	/**
	 * 连接创建时间
	 */
	private long connCreateTime;
	
	/**
	 * 连接创建超时时间
	 */
	private long connTimeOut;
	
	/**
	 * 消费者响应超时
	 */
	private int responseTimeOut;
	
	/**
	 * 消费结果：1-成功 2-失败
	 */
	private int consumeResult;
	
	/**
	 * 结束时间
	 */
	private String finishTime;
	
	/**
	 * 失败原因
	 */
	private String failedReason;
	
    /**
     * 重试次数
     */
	private Integer retryTimes;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public URLConnection getConn() {
		return conn;
	}

	public void setConn(URLConnection conn) {
		this.conn = conn;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public long getConnCreateTime() {
		return connCreateTime;
	}

	public void setConnCreateTime(long connCreateTime) {
		this.connCreateTime = connCreateTime;
	}

	public long getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(long connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public int getResponseTimeOut() {
		return responseTimeOut;
	}

	public void setResponseTimeOut(int responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}

	public int getConsumeResult() {
		return consumeResult;
	}

	public void setConsumeResult(int consumeResult) {
		this.consumeResult = consumeResult;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}
	
}
