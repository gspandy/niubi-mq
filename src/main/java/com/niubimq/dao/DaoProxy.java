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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 增加事务控制
 * @author junjin4838
 * @version 1.0
 */
public class DaoProxy extends BaseDao implements InvocationHandler {

	private final static Logger log = LoggerFactory.getLogger(DaoProxy.class);

	/**
	 * 被代理的对象
	 */
	private Object proxyedObject;

	public DaoProxy(Object o) {
		this.proxyedObject = o;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Object daoResult = null;
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			//注入新new的sqlSession对象
			((BaseDao)proxyedObject).setSqlSession(sqlSession);
			
			//调用实际的dao处理
			daoResult = method.invoke(proxyedObject, args);
			
			//事务提交
			sqlSession.commit();
		}catch(Exception e){
			sqlSession.rollback();
			log.error(e.getMessage());
		}finally{
			//关闭事务
			sqlSession.close();
		}
		
		return daoResult;
		
	}
}
