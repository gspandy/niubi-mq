##接口使用:
  查询生产者：
  ----------
      http://localhost:8080/nibimq/mqService/showProducer.do <br/>
      参数：producerSign <br/>
  创建生产者：
  ----------
       http://localhost:8080/nibimq/mqService/addProducer.do <br/>
       参数：producerSign  +  description <br/>
  删除生产者：
  ----------
       http://localhost:8080/nibimq/mqService/deleteProducer.do <br/>
       参数：producerSign  +  description <br/>
  创建消费者：
  ----------
       http://localhost:8080/nibimq/mqService/addConsumer.do <br/>
       参数：businessSign （必填）  +  description （必填） + callBackUrl（必填）+  <br/>
             allowRetryTimes (必填) +  responseTimeOut (必填) + requestType(必填) <br/>
