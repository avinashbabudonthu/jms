package com.jms;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

public class SendReceiveMessage {

	@Test
	public void send() throws NamingException, JMSException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL, "t3://localhost:7001");
		InitialContext initialContext = new InitialContext(environment);
		System.out.println("initialContext: " + initialContext);

		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext
				.lookup("jms/TestConnectionFactory");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = (Queue) initialContext.lookup("jms/TestJMSQueue");

		QueueSender queueSender = queueSession.createSender(queue);
		TextMessage textMessage = queueSession.createTextMessage();
		queueConnection.start();

		String message = "Welcome to Weblogic queue";
		textMessage.setText(message);
		queueSender.send(textMessage);

		queueSender.close();
		queueSession.close();
		queueConnection.close();
	}

	@Test
	public void receive() throws NamingException, JMSException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL, "t3://localhost:7001");
		InitialContext initialContext = new InitialContext(environment);
		System.out.println("initialContext: " + initialContext);

		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext
				.lookup("jms/TestConnectionFactory");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = (Queue) initialContext.lookup("jms/TestJMSQueue");
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);
		queueConnection.start();

		Message message = queueReceiver.receive();
		System.out.println(((TextMessage) message).getText());

		queueReceiver.close();
		queueSession.close();
		queueConnection.close();
	}

	@Test
	public void sendAndConsumeWithListener() throws NamingException, JMSException, InterruptedException {
		Hashtable<String, String> environment = new Hashtable<>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL, "t3://localhost:7001");
		InitialContext initialContext = new InitialContext(environment);
		System.out.println("initialContext: " + initialContext);

		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext
				.lookup("jms/TestConnectionFactory");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = (Queue) initialContext.lookup("jms/TestJMSQueue");
		QueueSender queueSender = queueSession.createSender(queue);
		TextMessage textMessage = queueSession.createTextMessage();
		String message = "Welcome to weblogic queue";
		textMessage.setText(message);
		queueSender.send(textMessage);
		System.out.println("Message Sent");

		QueueReceiver queueReceiver = queueSession.createReceiver(queue);
		queueReceiver.setMessageListener(new ReceiverListener());

		queueConnection.start();
		Thread.sleep(1000 * 60);

		queueSender.close();
		queueSession.close();
		queueConnection.close();
	}
}