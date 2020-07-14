package it.unibs.ing.domohouse.model.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.unibs.ing.domohouse.model.ModelStrings;

public class LogWriter {
	DateFormat dateFormat = new SimpleDateFormat(ModelStrings.LOG_DATE_FORMAT);

	public void write(String toWrite) {
		Date date = new Date();
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(ModelStrings.LOG_PATH + ModelStrings.LOG_NAME_FILE, true))) {
			bw.write("[" + dateFormat.format(date) + "] " + toWrite + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}