package com.niubimq.service;

import java.util.List;

import com.niubimq.pojo.Producer;

/**
 * @Description: 生产者信息管理
 * @author junjin4838
 * @date 2016年8月31日
 * @version 1.0
 */
public interface PCManageService {
	
	/**
	 * 查询生产者信息
	 * @param producerSign
	 * @return List
	 */
	public List<Producer> showProducer(String producerSign);
	
	/**
	 * 新增生产者信息
	 * @param producer
	 */
	public void addProducer(Producer producer);
	
	/**
	 * 删除生产者信息
	 * @param producer
	 */
	public void deleteProducer(Producer producer);
	
	/**
	 * 修改生产者信息
	 * @param producer
	 */
	public void updateProducer(Producer producer); 



}
