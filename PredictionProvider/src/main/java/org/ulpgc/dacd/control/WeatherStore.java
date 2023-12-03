package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.WeatherData;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherStore {
	private static final String DB_URL = "jdbc:sqlite:weather_data.db";

	public void saveWeatherData(String island, LocalDateTime dateTime, WeatherData weatherData) {
		try (Connection connection = DriverManager.getConnection(DB_URL);
		     Statement statement = connection.createStatement()) {

			statement.executeUpdate("CREATE TABLE IF NOT EXISTS \"" + island + "\" (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"date TEXT NOT NULL," +
					"temperature REAL NOT NULL," +
					"humidity INTEGER NOT NULL," +
					"wind_speed REAL NOT NULL," +
					"clouds_all INTEGER NOT NULL);");

			String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			boolean dataExists = false;
			try (PreparedStatement checkStatement = connection.prepareStatement(
					"SELECT COUNT(*) FROM \"" + island + "\" WHERE date = ?")) {
				checkStatement.setString(1, formattedDate);
				ResultSet resultSet = checkStatement.executeQuery();
				if (resultSet.next()) {
					int rowCount = resultSet.getInt(1);
					dataExists = rowCount > 0;
				}
			}

			if (dataExists) {
				try (PreparedStatement updateStatement = connection.prepareStatement(
						"UPDATE \"" + island + "\" SET temperature=?, humidity=?, wind_speed=?, clouds_all=? WHERE date=?")) {
					updateStatement.setDouble(1, weatherData.getMain().getTemp());
					updateStatement.setInt(2, weatherData.getMain().getHumidity());
					updateStatement.setDouble(3, weatherData.getWind().getSpeed());
					updateStatement.setInt(4, weatherData.getClouds().getAll());
					updateStatement.setString(5, formattedDate);

					int rowsAffected = updateStatement.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("Datos meteorológicos actualizados correctamente para " + island + " en la fecha " + formattedDate);
					} else {
						System.err.println("Error al actualizar datos meteorológicos para " + island + " en la fecha " + formattedDate + ". No se afectaron filas.");
					}
				}
			} else {
				try (PreparedStatement insertStatement = connection.prepareStatement(
						"INSERT INTO \"" + island + "\" (date, temperature, humidity, wind_speed, clouds_all) " +
								"VALUES (?, ?, ?, ?, ?)")) {

					insertStatement.setString(1, formattedDate);
					insertStatement.setDouble(2, weatherData.getMain().getTemp());
					insertStatement.setInt(3, weatherData.getMain().getHumidity());
					insertStatement.setDouble(4, weatherData.getWind().getSpeed());
					insertStatement.setInt(5, weatherData.getClouds().getAll());

					int rowsAffected = insertStatement.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("Datos meteorológicos guardados correctamente para " + island + " en la fecha " + formattedDate);
					} else {
						System.err.println("Error al insertar datos meteorológicos para " + island + " en la fecha " + formattedDate + ". No se afectaron filas.");
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL para la tabla " + island + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
}




