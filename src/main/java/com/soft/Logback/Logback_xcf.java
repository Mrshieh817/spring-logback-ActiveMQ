package com.soft.Logback;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * @author 作者:大飞
 * @version 创建时间：2018年4月28日 上午10:28:11 类说明
 */

public class Logback_xcf extends AppenderBase<LoggingEvent> implements MessageListener {
	public String providerURL;
	private String Account;
	private String Password; 
	public String getProviderURL() {
		return providerURL;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public void setProviderURL(String providerURL) {
		this.providerURL = providerURL;
	}

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();
	private static Destination destination;

	@Override
	public void start() {
		try {
			started = true;//设置连接为true
			connectionFactory = new ActiveMQConnectionFactory(Account, Password, providerURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (Exception e) {
		}
	}

	@Override
	protected void append(LoggingEvent eventObject) {
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

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

	}
}
