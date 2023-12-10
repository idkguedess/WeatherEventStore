package org.ulpgc.dacd.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

public class WeatherEvent {
	private LocalDateTime ts;
	private String ss;
	private LocalDateTime predictionTime;
	private Coord location;

	public WeatherEvent() {
	}

	public WeatherEvent(LocalDateTime ts, String ss, LocalDateTime predictionTime, Coord location) {
		this.ts = ts;
		this.ss = ss;
		this.predictionTime = predictionTime;
		this.location = location;
	}

	public String toJson() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(this);
		} catch (IOException e) {
			System.err.println("Error al convertir el evento a JSON: " + e.getMessage());
			return null;
		}
	}

	public static WeatherEvent deserialize(String eventJson) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(eventJson, WeatherEvent.class);
		} catch (IOException e) {
			System.err.println("Error al deserializar el evento JSON: " + e.getMessage());
			return null;
		}
	}
	public LocalDateTime getTs() {
		return ts;
	}

	public void setTs(LocalDateTime ts) {
		this.ts = ts;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public LocalDateTime getPredictionTime() {
		return predictionTime;
	}

	public void setPredictionTime(LocalDateTime predictionTime) {
		this.predictionTime = predictionTime;
	}

	public Coord getLocation() {
		return location;
	}

	public void setLocation(Coord location) {
		this.location = location;
	}
}
