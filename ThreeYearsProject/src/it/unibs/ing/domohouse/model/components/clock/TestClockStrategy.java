package it.unibs.ing.domohouse.model.components.clock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibs.ing.domohouse.model.ModelStrings;

public class TestClockStrategy implements ClockStrategy {
	private int hour;
	private int minute;
	private ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();

	public void startClock() {
		hour = 0;
		minute = 0;
		clock.scheduleAtFixedRate(new Runnable() {
			public void run() {
				updateTime();
			}
		}, 0, 2, TimeUnit.SECONDS); // ogni 2 secondi passa 1 minuto nel sistema
	}

	public String getCurrentTime() {
		assert clockInvariant() : ModelStrings.WRONG_INVARIANT;

		String string_hour = ModelStrings.NULL_CHARACTER + hour;
		String string_minute = ModelStrings.NULL_CHARACTER + minute;

		if (minute < 10) {
			string_minute = ModelStrings.CHAR_ZERO + minute;
		}
		if (hour < 10) {
			string_hour = ModelStrings.CHAR_ZERO + hour;
		}

		String result = string_hour + ModelStrings.CHAR_POINT + string_minute;

		assert result != null;
		assert clockInvariant() : ModelStrings.WRONG_INVARIANT;
		return result;
	}

	public void stopClock() {
		clock.shutdown();
	}

	private void updateTime() {
		assert clockInvariant() : ModelStrings.WRONG_INVARIANT;
		minute++;
		if (minute > 59) {
			minute = 0;
			hour++;
			if (hour > 23) {
				hour = 0;
			}
		}

		assert clockInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	private boolean clockInvariant() {
		boolean checkHour = hour >= 0 && hour <= 23;
		boolean checkMinute = minute >= 0 && minute <= 59;
		return checkHour && checkMinute;
	}
}
