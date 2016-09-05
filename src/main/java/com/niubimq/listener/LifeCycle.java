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

package com.niubimq.listener;
/**
 * @Description: 生命周期接口
 * @author junjin4838
 * @version 1.0
 */
public interface LifeCycle {
	
	public final int NEW = 0;
	
	public final int NEW_INITIALIZING = 1;
	
	public final int INITIALIZED = 2;
	
	public final int STARTING = 3;
	
	public final int RUNNING = 4;
	
	public final int DESTROYING = 5;
	
	public final int DESTROYED = 6;
	
	/**
	 * 服务初始化
	 */
	public void init();
	
	/**
	 * 服务启动
	 */
	public void start();
	
	/**
	 * 服务停止
	 */
	public void destory();

}
