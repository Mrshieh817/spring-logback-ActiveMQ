package com.soft.Logback;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * @author 作者:大飞
 * @version 创建时间：2018年4月28日 上午10:28:11 类说明(测试用例)
 */

public class OwnLog extends AppenderBase<LoggingEvent> {
	// (测试用例)
	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();
	private static Destination destination;

	public OwnLog() throws Exception {
		connectionFactory = new ActiveMQConnectionFactory("", "", "tcp://192.168.139.128:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(true, Session.SESSION_TRANSACTED);
	}

	@Override
	protected void append(LoggingEvent eventObject) {
		// LoggingEvent event = (LoggingEvent) ((ActiveMQObjectMessage)
		// message).getObject();
		try {
			destination = session.createQueue("Jaycekon-MQ");
			MessageProducer messageProducer = null;
			if (threadLocal.get() != null) {
				messageProducer = threadLocal.get();
			} else {
				messageProducer = session.createProducer(destination);
				threadLocal.set(messageProducer);
			}
			TextMessage msg = session.createTextMessage(eventObject.getMessage());
			System.out.println(eventObject.getMessage());
			messageProducer.send(msg);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("Received log [" + eventObject.getLevel() + "]: " + eventObject.getMessage());
		System.out.println("自定义的哦！！！" + eventObject.getMessage());
	}
}
