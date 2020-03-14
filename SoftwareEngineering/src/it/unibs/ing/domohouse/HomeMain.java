package it.unibs.ing.domohouse;

import it.unibs.ing.domohouse.components.Clock;
import it.unibs.ing.domohouse.util.InterfaceHandler;

public class HomeMain {
	
	private static InterfaceHandler interfaceHandler;

	
	public static void main(String[] args) {
		Clock.startClock(); //start system clock
		interfaceHandler = new InterfaceHandler();
		interfaceHandler.show();
	}
}
