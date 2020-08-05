package it.unibs.ing.domohouse.model.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import it.unibs.ing.domohouse.model.ModelStrings;

public class LogWriter {
	private Logger logger = Logger.getLogger(LogWriter.class.getName());

	public LogWriter() throws Exception {
		try {
			LogManager.getLogManager().reset();
			System.setProperty("java.util.logging.SimpleFormatter.format",
					"%1$tFT%1$tT.%1$tL%1$tz [%4$s] %2$s: %5$s %6$s%n");
			Handler handler = new FileHandler(ModelStrings.LOG_PATH + ModelStrings.LOG_NAME_FILE);
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
		}
		catch (SecurityException | IOException e) {
			throw new Exception("Errore durante la configurazione del logging", e);
		}
		logger.setLevel(Level.FINE);
	}

	public void log(Level severity, String logMessage) {
		logger.log(severity, logMessage);
	}

	public void log(Level severity, String logMessage, Throwable thrown) {
		logger.log(severity, logMessage, thrown);
	}
}