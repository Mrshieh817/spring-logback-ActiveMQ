package com.soft.island;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/home")
public class HomeController {
	  private Logger log = LoggerFactory.getLogger(HomeController.class); 
	
	
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public Object test() {
		log.error("开始记录error日志测试--来自HomeController");
		log.info("开始记录info日志测试--来自HomeController");
		log.debug("开始记录debug日志测试--来自HomeController");
		Map<String , Object> list=new HashMap<>();
		list.put("id", 123456);
		list.put("name", "王大山");
		return list;
	}

}
