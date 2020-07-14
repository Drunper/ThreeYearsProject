package it.unibs.ing.domohouse.model.components.clock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RealClockStrategy implements ClockStrategy {

	@Override
	public void startClock() {
	}

	@Override
	public String getCurrentTime() {
		System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("HH.mm");
		Date date = new Date(System.currentTimeMillis());
		String currentTime = formatter.format(date);
		return currentTime;
	}

	@Override
	public void stopClock() {
	}

}
