package com.soft.ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import javax.jms.*;

/**
 * @author 作者:大飞
 * @version 创建时间：2018年4月28日 下午3:16:04 类说明
 */

public class Message implements MessageListener {

	public Message() throws Exception {
		// (测试用例)
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.139.128:61616");
		Connection connection = factory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
		// 注意这里JMSAppender只支持TopicDestination//
		Destination topicDestination = session.createQueue("Jaycekon-MQ");
		MessageConsumer consumer = session.createConsumer(topicDestination);
		consumer.setMessageListener(this);

		// log a message
		Logger logger = Logger.getLogger(Message.class);
		logger.info("Info Log.");
		logger.warn("Warn Log");
		logger.error("Error Log.");

		// clean up
		Thread.sleep(1000);
		consumer.close();
		session.close();
		connection.close();
		System.exit(1);
	}

	public void onMessage(javax.jms.Message message) {
		try {
			// receive log event in your consumer
			LoggingEvent event = (LoggingEvent) ((ActiveMQObjectMessage) message).getObject();
			System.out.println("Received log [" + event.getLevel() + "]: " + event.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Message();
	}

}