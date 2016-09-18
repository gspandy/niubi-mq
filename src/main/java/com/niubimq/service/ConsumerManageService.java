package com.niubimq.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.niubimq.pojo.Consumer;

@Service
public interface ConsumerManageService {
	
    /**
     * 新增消费者信息
     * */
    public void addConsumer(Consumer consumer);
    
    /**
     * 删除消费者信息
     * */
    public void deleteConsumer(Consumer c);
    
    /**
     * 修改消费者信息
     * */
    public void updateConsumer(Consumer consumer);
    
    /**
     * 查询新增消费者信息
     * */
    public List<Consumer> showConsumer();
    
    /**
     * 校验消费者内置参数
     * @return
     */
    String validateConsumerParams(Consumer consumer);
}
