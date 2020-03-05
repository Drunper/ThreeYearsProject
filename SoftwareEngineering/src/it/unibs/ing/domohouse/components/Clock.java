package it.unibs.ing.domohouse.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS); //ogni 2 secondi passa 1 minuto nel sistema
	}
	
	public static int getHour() {
		assert clockInvariant() : "Invariante della classe non soddisfatto";
		return hour;
	}
	
	public static int getMinute() {
		assert clockInvariant() : "Invariante della classe non soddisfatto";
		return minute;
	}
	
	public static String getCurrentTime() {
		assert clockInvariant() : "Invariante della classe non soddisfatto";

		String string_hour = "" + hour;
		String string_minute = "" + minute;
		
		if(minute < 10) {
			string_minute = "0" + minute;
		}
		if(hour < 10) {
			string_hour = "0" + hour;
		}
		
		String result = string_hour + "." + string_minute;
		
		assert result != null;
		assert clockInvariant() : "Invariante della classe non soddisfatto";
		return result;
	}
	
	private static void updateTime() {
		assert clockInvariant() : "Invariante della classe non soddisfatto";
		
		minute++;
		if(minute > 59) {
			minute = 0;
			hour++;
			if(hour > 23) {
				hour = 0;
			}
		}
		
		assert clockInvariant() : "Invariante della classe non soddisfatto";
	}
	
	private static boolean clockInvariant() {
		boolean checkHour = hour >= 0 && hour <= 23;
		boolean checkMinute = minute >= 0 && minute <= 59;
		if(checkHour && checkMinute) return true;
		return false;
	}
}
