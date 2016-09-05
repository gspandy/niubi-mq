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

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @Description: mysql数据库连接基础类
 * @author junjin4838
 * @version 1.0
 */
public class BaseDao {
	 
	protected static SqlSessionFactory sqlSessionFactory;
	
	protected SqlSession sqlSession;
	
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	public void setSqlSession(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}
	
	static{
		String resource = "mybatis-config.xml";
		InputStream reader = null;
		try {
			reader = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
	}

}
