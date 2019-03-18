package ibm.mq.send.receive;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;

public class SendRecieveTextMessage {

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

	@Test
	public void receive() throws JMSException {
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("localhost");
		cf.setPort(1414);
		cf.setQueueManager("qm_demo2");
		cf.setChannel("SYSTEM.ADMIN.SVRCONN");

		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		MQQueue mqQueue = (MQQueue) session.createQueue("queue:///QL1");
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(mqQueue);

		// start the connection
		connection.start();

		Message message = receiver.receive();
		System.out.println(
				SendRecieveTextMessage.class.getName() + ".receive(): " + message.getBody(String.class));
	}
}