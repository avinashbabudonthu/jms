package com.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ReceiverListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println(ReceiverListener.class.getSimpleName() + ".onMessage(" + message + ")");

		if (message instanceof TextMessage) {
			try {
				System.out.println(((TextMessage) message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
