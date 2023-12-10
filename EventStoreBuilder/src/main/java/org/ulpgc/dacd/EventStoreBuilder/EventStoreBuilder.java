package org.ulpgc.dacd.EventStoreBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventStoreBuilder {
	public static void main(String[] args) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("prediction.Weather");
			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(new EventMessageListener());

			System.out.println("EventStoreBuilder esperando eventos...");
			Thread.sleep(Long.MAX_VALUE);

			consumer.close();
			session.close();
			connection.close();
		} catch (JMSException | InterruptedException e) {
			System.err.println("Error en el EventStoreBuilder: " + e.getMessage());
		}
	}
}
