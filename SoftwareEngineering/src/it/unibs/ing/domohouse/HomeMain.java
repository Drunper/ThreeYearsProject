package it.unibs.ing.domohouse;

import it.unibs.ing.domohouse.components.Clock;
import it.unibs.ing.domohouse.util.InterfaceHandler;

public class HomeMain {
	
	private static InterfaceHandler interfaceHandler;

	
	//version  4
	public static void main(String[] args) {
		Clock.startClock();
		interfaceHandler = new InterfaceHandler();
		interfaceHandler.show();
	}
}
