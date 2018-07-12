package com.soft.ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.jms.*;

/**
 * @author 作者:大飞
 * @version 创建时间：2018年4月28日 下午3:16:04 类说明
 */

public class Message implements MessageListener {
	private Logger log = LoggerFactory.getLogger(Message.class);
	public Message() throws Exception {
		// (测试用例)
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.139.128:61616");
		Connection connection = factory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
		// 注意这里JMSAppender只支持TopicDestination//
		Destination topicDestination = session.createQueue("Jaycekon-MQ");
		MessageConsumer consumer = session.createConsumer(topicDestination);
		consumer.setMessageListener(this);

		// log a message
	
		log.debug("debug Log.");
		log.warn("Warn Log");
		log.error("Error Log.");

		// clean up
		Thread.sleep(1000);
		consumer.close();
		session.close();
		connection.close();
		System.exit(1);
	}

	public void onMessage(javax.jms.Message msg) {
		/*try {
			// receive log event in your consumer
			LoggingEvent event = (LoggingEvent) ((ActiveMQObjectMessage) message).getObject();
			System.out.println("Received log [" + event.getLevel() + "]: " + event.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		// TODO Auto-generated method stub
		TextMessage message = (TextMessage)msg;
		try {
			log.info("receive message : "+ message.getText());
			System.err.println("receive message : "+ message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		new Message();
	}

}