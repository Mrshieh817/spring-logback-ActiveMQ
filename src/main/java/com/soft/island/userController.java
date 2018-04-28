package com.soft.island;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @author 作者:大飞
* @version 创建时间：2018年4月28日 上午11:27:32
* 类说明
*/
@Controller
@RequestMapping(value = "/user")
public class userController {
	  private Logger log = LoggerFactory.getLogger(userController.class); 
	
	
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public Object test() {
		log.error("开始记录error日志测试--来自userController");
		log.info("开始记录info日志测试--来自userController");
		log.debug("开始记录debug日志测试--来自userController");
		Map<String , Object> list=new HashMap<>();
		list.put("id", 456789);
		list.put("name", "王xiao山");
		return list;
	}

}