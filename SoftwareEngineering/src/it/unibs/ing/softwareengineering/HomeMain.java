package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeMain {
	
	private static String[] loginChoices = {Strings.USER, Strings.MAINTAINER};
	private static String[] userChoices = {Strings.HOUSE_VIEW};
	private static Scanner s = new Scanner(System.in);
	
	//TEST OBJECTS (will be deleted and loaded from files)
	private HousingUnit casetta = new HousingUnit("")
	
	//THE BIG MAIN (it's fu***ng huge)
	public static void main(String[] args) {
		MyMenu loginMenu = new MyMenu(Strings.LOGINTITLE, loginChoices);
		MyMenu userMenu = new MyMenu(Strings.USER_MENU, userChoices);
		do {
			int choice = loginMenu.scegli();
			switch(choice) {
				case 1:
					System.out.println(Strings.INSERT_NAME);
					HomeLogin user = new HomeLogin(s.nextLine());
					System.out.println("OK");
					break;
				case 2:
					System.out.println(Strings.INSERT_NAME);
					System.out.println(Strings.INSERT_MANPASS);
					HomeLogin maintainer = new HomeLogin(s.nextLine(), s.nextLine());
					System.out.println("OK");
					break;
				}
		}
		while(true);	
		
	}
}
