package com.niubimq.service;

import java.util.List;
import java.util.Map;

/**
 * @Description:生产者 - 消费者 对应关系
 * @author junjin4838
 * @version 1.0
 */
public interface PCMapManageService {

	/**
	 * 展现生产者-消费者对应关系
	 * @param pcMap
	 * @return
	 */
	List<Map<String, String>> showSubscriber(Map<String, String> pcMap);

	/**
	 * 删除生产者-消费者对应关系
	 * @param pcMap
	 */
	void deleteSubscriber(Map<String, String> pcMap);

	/**
	 * 增加生产者-消费者对应关系
	 * @param pcMap
	 */
	void addSubscriber(Map<String, String> pcMap);

}
