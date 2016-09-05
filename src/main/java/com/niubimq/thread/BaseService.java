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

package com.niubimq.thread;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niubimq.dao.MsgPushDao;
import com.niubimq.listener.LifeCycle;

/**
 * @Description: 服务基类
 * @author junjin4838
 * @version 1.0
 */
@Service
public abstract class BaseService implements LifeCycle,Runnable {
	
	@Autowired
	public MsgPushDao msgPushDao;
	
	/**
	 * 服务当前的状态
	 */
	protected int state;
	
	/**
	 * 服务注册的监听器
	 */
	private LifecycleListener[] listeners;
	
	
	
	/**
	 * 服务初始化
	 */
	public final void init() {
		initInternal();
		this.state = LifeCycle.INITIALIZED;
		setStateInternal(this.state);
	}
	
	/**
	 * 继承类要具体实现的方法 -- 初始化
	 */
	public abstract void initInternal();
	
    /**
     * 服务启动
     */
	public void start() {
		this.state = LifeCycle.STARTING;
		setStateInternal(this.state);
		startInternal();
		this.state = LifeCycle.RUNNING;
		setStateInternal(this.state);
	}
	
	/**
	 * 继承类要具体实现的方法 -- 服务启动
	 */
	public abstract void startInternal();
	

	/**
	 * 服务销毁
	 */
	public void destory() {
		destroyInternal();
		this.state = LifeCycle.DESTROYED;
		setStateInternal(this.state);
	}
	
	/**
	 * 继承类要具体实现的方法 -- 服务销毁
	 */
	public abstract void destroyInternal();
	
	
	/**
	 * 触发监听器
	 * @param state
	 */
	private void setStateInternal(int state){
		if(listeners == null) return;
		LifecycleListener interested[] = listeners;
		for(int i=0;i<interested.length;i++){
			interested[i].lifecycleEvent(state);
		}
		
	}
}
