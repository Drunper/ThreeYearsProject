package it.unibs.ing.domohouse.components;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibs.ing.domohouse.util.Strings;

public class Clock {
	private static int hour;
	private static int minute;
	private static ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();
	
	public static void startClock() {
		hour = 0;
		minute = 0; 
		clock.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
					updateTime();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS); //ogni 2 secondi passa 1 minuto nel sistema
	}
	
	public static int getHour() {
		assert clockInvariant() : Strings.WRONG_INVARIANT;
		return hour;
	}
	
	public static int getMinute() {
		assert clockInvariant() : Strings.WRONG_INVARIANT;
		return minute;
	}
	
	public static String getCurrentTime() {
		assert clockInvariant() : Strings.WRONG_INVARIANT;

		String string_hour = Strings.NULL_CHARACTER + hour;
		String string_minute = Strings.NULL_CHARACTER + minute;
		
		if(minute < 10) {
			string_minute = Strings.CHAR_ZERO + minute;
		}
		if(hour < 10) {
			string_hour = Strings.CHAR_ZERO + hour;
		}
		
		String result = string_hour + Strings.CHAR_POINT + string_minute;
		
		assert result != null;
		assert clockInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	private static void updateTime() {
		assert clockInvariant() : Strings.WRONG_INVARIANT;
		
		minute++;
		if(minute > 59) {
			minute = 0;
			hour++;
			if(hour > 23) {
				hour = 0;
			}
		}
		
		assert clockInvariant() : Strings.WRONG_INVARIANT;
	}
	
	private static boolean clockInvariant() {
		boolean checkHour = hour >= 0 && hour <= 23;
		boolean checkMinute = minute >= 0 && minute <= 59;
		return checkHour && checkMinute;
	}
}
