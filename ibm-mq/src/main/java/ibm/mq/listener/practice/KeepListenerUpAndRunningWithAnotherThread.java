package ibm.mq.listener.practice;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;

import ibm.mq.listener.MQMessageListener;

/**
 * Steps to execute this example
 * 
 * 1. Make sure IBM MQ is up and running in local
 * 2. Execute startListenerWithThread() method as JUnit
 * 3. Execute send() method as JUnit
 * 
 * @author Avinash Babu Donthu
 *
 */
public class KeepListenerUpAndRunningWithAnotherThread {

	@Test
	public void startListenerWithThread() {
		ListenerThread listenerThread = new ListenerThread();
		listenerThread.start();

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void send() {
		try {
			MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
			cf.setHostName("localhost");
			cf.setPort(1414);
			// cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			cf.setQueueManager("qm_demo2");
			cf.setChannel("SYSTEM.ADMIN.SVRCONN");

			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
			// MQQueueConnection connection = cf.createConnection("userID", "password");

			MQQueueSession session = (MQQueueSession) connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MQQueue queue = (MQQueue) session.createQueue("queue:///QL1");
			MQQueueSender sender = (MQQueueSender) session.createSender(queue);

			TextMessage message = (TextMessage) session.createTextMessage("Welcome to IBM Mq");

			// start the connection
			connection.start();
			sender.send(message);

			System.out.println("Sent message to queue: " + message.getText());

			sender.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public class ListenerThread extends Thread {

		@Override
		public void run() {
			Thread t1 = new Thread(() -> {
				try {
					MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
					cf.setHostName("localhost");
					cf.setPort(1414);
					// cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
					cf.setQueueManager("qm_demo2");
					cf.setChannel("SYSTEM.ADMIN.SVRCONN");

					MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
					MQQueueSession session2 = (MQQueueSession) connection.createQueueSession(false,
							Session.AUTO_ACKNOWLEDGE);
					MQQueue mqQueue = (MQQueue) session2.createQueue("queue:///QL1");

					// start the connection
					connection.start();

					MQQueueReceiver receiver = (MQQueueReceiver) session2.createReceiver(mqQueue);
					receiver.setMessageListener(new MQMessageListener());

				} catch (JMSException e) {
					e.printStackTrace();
				}
			});

			t1.start();
			System.out.println(ListenerThread.class.getName() + " started.");
			for (;;) {
			}
		}
	}

}