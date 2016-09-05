package rabbaitmq.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.niubimq.util.PropertiesReader;

/**
 * @Description: 配置文件读取测试
 * @author junjin4838
 * @date 2016年9月4日
 * @version 1.0
 */
public class PropertiesUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		PropertiesReader reader = PropertiesReader.getInstance();
		assertEquals("2000", reader.get("thread.TimmingMsgSaveService.sleeptime"));
	}

}
