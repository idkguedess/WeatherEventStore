package org.ulpgc.dacd.EventStoreBuilder;

import org.ulpgc.dacd.control.WeatherEvent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class EventMessageListener implements MessageListener {
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) message;
				String eventJson = textMessage.getText();
				WeatherEvent weatherEvent = WeatherEvent.deserialize(eventJson);

				EventStore.storeEvent(weatherEvent);
			} catch (JMSException e) {
				System.err.println("Error al procesar el mensaje JMS: " + e.getMessage());
			}
		}
	}
}
