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
 * 
 * @author junjin4838
 *
 */
public class Consumer {

    /**
     * 消费者标识
     * */
    private String businessSign;
    
    /**
     * 消费者描述
     * */
    private String description;
    
    /**
     * 消费地址
     * */
    private String callBackUrl;
    
    /**
     * 允许重试的次数
     * */
    private Integer allowRetryTimes;
    
    /**
     * 超时时间
     * */
    private Integer responseTimeOut;

    /**
     * 消息请求方式:1-http get;2-http post
     * */
    private Integer requestType;
    
    /**
     * 创建时间
     * */
    private String createTime;
    
    /**
     * 更新时间
     * */
    private String updateTime;

    public String getBusinessSign() {
        return businessSign;
    }

    public void setBusinessSign(String businessSign) {
        this.businessSign = businessSign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getResponseTimeOut() {
        return responseTimeOut;
    }

    public void setResponseTimeOut(Integer responseTimeOut) {
        this.responseTimeOut = responseTimeOut;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }
}
