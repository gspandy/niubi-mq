##接口使用:
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
