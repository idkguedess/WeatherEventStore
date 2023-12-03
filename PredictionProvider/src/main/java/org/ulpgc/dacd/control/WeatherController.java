package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.WeatherData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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

				System.out.println("Datos meteorológicos actualizados correctamente para " + island + " en la fecha " + dateTime);
			}
		} catch (IOException e) {
			System.err.println("Error al obtener los datos meteorológicos: " + e.getMessage());
		}
	}
}

