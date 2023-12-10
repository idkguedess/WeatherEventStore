package org.ulpgc.dacd.control;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.ulpgc.dacd.model.WeatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OpenWeatherMapProvider implements WeatherProvider {
	private static final String API_KEY = "4fca9117f3ac0d66b70f8519ec987c00";
	private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather";

	@Override
	public WeatherData getWeatherData(double latitude, double longitude, LocalDateTime dateTime) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();

		String url = String.format("%s?lat=%f&lon=%f&dt=%d&appid=%s&units=metric",
				API_ENDPOINT, latitude, longitude, dateTime.toEpochSecond(ZoneOffset.UTC), API_KEY);

		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			StringBuilder responseStringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				responseStringBuilder.append(line);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.readValue(responseStringBuilder.toString(), WeatherData.class);
		} else {
			throw new IOException("Error en la solicitud HTTP: " + httpResponse.getStatusLine().getStatusCode());
		}
	}
}