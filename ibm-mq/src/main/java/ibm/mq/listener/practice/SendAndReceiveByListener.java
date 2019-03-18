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

public class SendAndReceiveByListener {

	@Test
	public void sendAndReceiveByListener() {
		try {
			MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
			cf.setHostName("localhost");
			cf.setPort(1414);
			// cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			cf.setQueueManager("qm_demo2");
			cf.setChannel("SYSTEM.ADMIN.SVRCONN");

			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
			// MQQueueConnection connection = cf.createConnection("userID", "password");

			// create sender
			MQQueueSession session = (MQQueueSession) connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MQQueue queue = (MQQueue) session.createQueue("queue:///QL1");
			MQQueueSender sender = (MQQueueSender) session.createSender(queue);

			// create receiver
			MQQueueSession session2 = (MQQueueSession) connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MQQueue mqQueue = (MQQueue) session2.createQueue("queue:///QL1");
			MQQueueReceiver receiver = (MQQueueReceiver) session2.createReceiver(mqQueue);
			receiver.setMessageListener(new MQMessageListener());

			// start the connection
			connection.start();

			TextMessage message = (TextMessage) session.createTextMessage("Welcome to IBM Mq");
			sender.send(message);
			System.out.println("Sent message to queue: " + message.getText());

			sender.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}