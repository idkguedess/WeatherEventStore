package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Coord;
import org.ulpgc.dacd.model.WeatherData;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherController {
	private WeatherProvider weatherProvider;
	private WeatherStore weatherStore;

	public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
		this.weatherProvider = weatherProvider;
		this.weatherStore = weatherStore;
	}

	public void updateWeatherData(String island, double latitude, double longitude, LocalDate date) {
		try {
			for (int day = 0; day < 5; day++) {
				LocalTime time = LocalTime.of(12, 0);
				LocalDateTime dateTime = LocalDateTime.of(date.plusDays(day), time);

				WeatherData weatherData = weatherProvider.getWeatherData(latitude, longitude, dateTime);
				weatherStore.saveWeatherData(island, dateTime, weatherData);

				System.out.println("Datos meteorológicos obtenidos correctamente para " + island + " en la fecha " + dateTime);

				WeatherEvent weatherEvent = new WeatherEvent();
				weatherEvent.setTs(LocalDateTime.now());
				weatherEvent.setSs("prediction-provider");
				weatherEvent.setPredictionTime(dateTime);
				Coord eventLocation = new Coord(latitude, longitude);
				weatherEvent.setLocation(eventLocation);

				sendEventToActiveMQ(weatherEvent);
			}
		} catch (IOException e) {
			System.err.println("Error al obtener los datos meteorológicos: " + e.getMessage());
		}
	}

	private void sendEventToActiveMQ(WeatherEvent weatherEvent) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("prediction.Weather");
			MessageProducer producer = session.createProducer(destination);

			ObjectMapper objectMapper = new ObjectMapper();
			String eventJson = objectMapper.writeValueAsString(weatherEvent);

			TextMessage message = session.createTextMessage(eventJson);

			producer.send(message);

			producer.close();
			session.close();
			connection.close();
		} catch (JMSException | IOException e) {
			System.err.println("Error al enviar el evento a ActiveMQ: " + e.getMessage());
		}
	}
}
