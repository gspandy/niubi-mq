package com.niubimq.dao.impl;


import java.util.List;

import com.niubimq.dao.BaseDao;
import com.niubimq.dao.ConsumerManageDao;
import com.niubimq.pojo.Consumer;

/**
 * 消费者信息使用的Dao
 * 
 * */
public class ConsumerManageDaoImpl extends BaseDao implements ConsumerManageDao {

	public void insertConsumer(Consumer consumer) {
		if(consumer==null)return;
        sqlSession.insert("insertConsumer", consumer);
		
	}

	public void deleteConsumer(Consumer consumer) {
		if(consumer==null)return;
		sqlSession.update("deleteConsumer", consumer);
		
	}

	public void updateConsumer(Consumer consumer) {
		sqlSession.update("updateConsumer",consumer);
	}

	public List<Consumer> selectConsumer() {
		return sqlSession.selectList("selectConsumer");
	}

}
