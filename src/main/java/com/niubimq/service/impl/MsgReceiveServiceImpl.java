package com.niubimq.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niubimq.dao.MsgReceiveServiceDao;
import com.niubimq.pojo.Consumer;
import com.niubimq.service.MsgReceiveService;

/**
 * @Description: 
 * @author junjin4838
 * @version 1.0
 */
@Service("msgReceiveService")
public class MsgReceiveServiceImpl implements MsgReceiveService {
	
	@Autowired
	public MsgReceiveServiceDao productDao;
	

	/**
	 * 获取所有消费者
	 */
	public HashMap<String, Consumer> getConsumers() {
		
		HashMap<String, Consumer> consumers = new HashMap<String, Consumer>();
		
		List<Consumer> consumerList = productDao.getConsumers();
	    for(Consumer c : consumerList){
	    	consumers.put(c.getBusinessSign(), c);
	    }
	    
		return consumers;
		
	}

	/**
	 * 生产者 - 消费者 对应关系
	 * producerSign -- businessSign[] 
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, String[]> getPCMapList() {
		
		List<Map> pcMapList =  productDao.getPCMapList();
		
		HashMap<String, String> tempPcMap = new HashMap<String, String>();
		
		for(Map pcMap : pcMapList){
            tempPcMap.put((String)pcMap.get("producerSign"), tempPcMap.get(pcMap.get("producerSign")) + "," + pcMap.get("businessSign"));
        }
		
        HashMap<String, String[]> pcMap = new HashMap<String, String[]>();
        
        Iterator iter = tempPcMap.entrySet().iterator();
        
        while(iter.hasNext()){
            Entry entry = (Entry) iter.next();
            pcMap.put((String) entry.getKey(), ((String) entry.getValue()).substring(5).split(","));
        }
		
		return pcMap;
	}

}
