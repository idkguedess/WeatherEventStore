package org.ulpgc.dacd.control;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	public static void main(String[] args) {
		WeatherProvider weatherProvider = new OpenWeatherMapProvider();
		WeatherStore weatherStore = new WeatherStore();
		WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				weatherController.updateWeatherData("Gran_Canaria", 28.1288694, -15.4349015, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("Tenerife", 28.469648, -16.2540884, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("Lanzarote", 28.0379237, -15.5801718, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("Fuerteventura", 28.40037685, -14.004869713542156, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("La_Graciosa", 29.253779700000003, -13.506207973612284, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("La_Palma", 28.6552318, -17.85732234254766, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("El_Hierro", 27.74350835, -18.038179618209902, LocalDate.now().plusDays(1));
				weatherController.updateWeatherData("La_Gomera", 28.119242, -17.225640691220967, LocalDate.now().plusDays(1));
			}
		}, 0, 6 * 60 * 60 * 1000);
	}
}