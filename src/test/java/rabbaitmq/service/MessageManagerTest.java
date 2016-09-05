package rabbaitmq.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.niubimq.client.MessageConsumerService;
import com.niubimq.client.MessageProductorService;

/**
 * @Description:
 * @author junjin4838
 * @date 2016年9月1日
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-amqp-rabbit.xml")
public class MessageManagerTest {

	@Resource
	private MessageProductorService messageProductorService;
	@Resource
	private MessageConsumerService messageConsumer;

	@Test
	public void testMessageQueue() {
		messageProductorService.pushToMessageQueue("foo_queue_one","hello giraffe");
		// messageProductorService.popMessage("rabbit_queue_one");
	}

}
