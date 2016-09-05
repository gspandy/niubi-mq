package rabbaitmq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.niubimq.pojo.Message;

/**
 * 发送消息到MQ测试
 * @author junjin4838
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MQSendClient {

	@Test
	public void testSendMsgToQueue() {

		ApplicationContext context = new GenericXmlApplicationContext("classpath:/spring-rabbit.xml");
		AmqpTemplate template = context.getBean(AmqpTemplate.class);

		Message msg = new Message();
		msg.setAllowRetryTimes(5);
		msg.setBusinessSign("consumer-a");
		msg.setCallBackUrl("http://localhost:15672");
		msg.setMessageContent("{abc:abc}");
		msg.setMessageType(1);
		msg.setProducerSign("producer-a");
		msg.setRequestType(1);
		msg.setResponseTimeOut(5);

		template.convertAndSend(msg.toString());

	}

}
