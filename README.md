##接口使用:
  消息发送：
  ----------
      http://localhost:8080/nibimq/mqService/sendMsg.do
      参数：producerSign + businessSign + businessSign + allowRetryTimes + planTime （null - 即时发送；有数据 - 定时发送）
  查询生产者：
  ----------
      http://localhost:8080/nibimq/mqService/showProducer.do 
      参数：producerSign 
  创建生产者：
  ----------
       http://localhost:8080/nibimq/mqService/addProducer.do 
       参数：producerSign  +  description 
  删除生产者：
  ----------
       http://localhost:8080/nibimq/mqService/deleteProducer.do 
       参数：producerSign  +  description 
  创建消费者：
  ----------
       http://localhost:8080/nibimq/mqService/addConsumer.do 
       参数：businessSign + description + callBackUrl + allowRetryTimes + responseTimeOut + requestType
  查询消费者：
  ----------
       http://localhost:8080/nibimq/mqService/showConsumer.do  
  更新消费者：
  ----------
       http://localhost:8080/nibimq/mqService/updateConsumer.do
       参数：businessSign + description + callBackUrl + allowRetryTimes + responseTimeOut + requestType
  删除消费者：
  ----------
       http://localhost:8080/nibimq/mqService/deleteConsumer.do
  新增消费者 - 生产者 关系：
  ----------
       http://localhost:8080/nibimq/mqService/addSubscriber.do
       参数：businessSign  +  producerSign
  查询消费者 - 生产者 关系：
  ----------
       http://localhost:8080/nibimq/mqService/showSubscriber.do
       参数：businessSign  +  producerSign
  删除消费者 - 生产者 关系：
  ----------
       http://localhost:8080/nibimq/mqService/deleteSubscriber.do 
       参数：businessSign  +  producerSign
