package it.unibs.ing.domohouse;

import it.unibs.ing.domohouse.util.InterfaceHandler;

public class HomeMain {
	
	private static InterfaceHandler interfaceHandler;
	
	//version  3 
	public static void main(String[] args) {
		interfaceHandler = new InterfaceHandler();
		interfaceHandler.show();
	}
}
