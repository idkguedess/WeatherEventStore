package org.ulpgc.dacd.EventStoreBuilder;

import org.ulpgc.dacd.control.WeatherEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventStore {
	public static void storeEvent(WeatherEvent weatherEvent) {
		try {
			String directoryPath = determineDirectoryPath(weatherEvent.getTs());
			String filePath = directoryPath + "/" + weatherEvent.getSs() + ".events";

			try (FileWriter fileWriter = new FileWriter(filePath, true)) {
				fileWriter.write(weatherEvent.toJson() + "\n");
				System.out.println("Evento almacenado en: " + filePath);
			}
		} catch (IOException e) {
			System.err.println("Error al almacenar el evento: " + e.getMessage());
		}
	}

	private static String determineDirectoryPath(LocalDateTime ts) {
		String formattedDate = ts.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String directoryPath = "eventstore/prediction.Weather/" + formattedDate;

		File directory = new File(directoryPath);
		if (!directory.exists()) {
			boolean created = directory.mkdirs();
			if (!created) {
				System.err.println("Error al crear el directorio: " + directoryPath);
			}
		}

		return directoryPath;
	}
}

