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

import com.niubimq.pojo.Message;


public interface MsgPushDao {

    /**
     * 保存消费过的数据（单条保存）
     * */
    public void saveConsumedMsg(Message msg);
    
    /**
     * 保存消费过的数据（批量保存）
     * */
    public void saveConsumedMsg(List<Message> msgList);
    
    /**
     * 保存定时消息数据（单条保存）
     * */
    public void saveTimmingMsg(Message msg);
    
    /**
     * 保存定时消息数据（批量保存）
     * */
    public void saveTimmingMsg(List<Message> msgList);
    
    /**
     * 获取定时消息数据
     * */
    public List<Message> getTimmingMsg(String dataLock);
    
    /**
     * 锁定消息数据
     * */
    public void lockTimmingMsg(String dataLock);
    
    /**
     * 删除定时消息数据
     * */
    public void delTimmingMsg(String dataLock);
}
