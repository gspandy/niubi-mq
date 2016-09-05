package rabbaitmq.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: 
 * @author junjin4838
 * @date 2016年9月1日
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath/config:spring-amqp-rabbit.xml")
public class TestBase {

}
