package com.niubimq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 代码测试
 * @author junjin4838
 * @version 1.0
 */

@Controller
@RequestMapping("/mqService")
public class CallBackUrlTest {
	
   @RequestMapping("/test")	
   public void test(){
	   System.out.println("消费者回调URL成功");
   }

}
