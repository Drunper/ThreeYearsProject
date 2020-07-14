package it.unibs.ing.domohouse.model.components.clock;

public interface ClockStrategy {

	void startClock();
	String getCurrentTime();
	void stopClock();
}
