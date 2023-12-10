package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Coord;

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
