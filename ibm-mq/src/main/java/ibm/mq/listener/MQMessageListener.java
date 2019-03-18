package ibm.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MQMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println();

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out
						.println(MQMessageListener.class.getName() + "onMessage(): " + textMessage.getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
