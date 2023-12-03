package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.WeatherData;

import java.io.IOException;
import java.time.LocalDateTime;

public interface WeatherProvider {
	WeatherData getWeatherData(double latitude, double longitude, LocalDateTime localDateTime) throws IOException;
}