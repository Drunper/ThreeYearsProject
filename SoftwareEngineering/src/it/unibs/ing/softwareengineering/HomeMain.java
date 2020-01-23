package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeMain {
	
	private static String[] loginChoices = {Strings.USER, Strings.MAINTAINER};
	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		MyMenu login = new MyMenu(Strings.LOGINTITLE, loginChoices);
		do {
			int choice = login.scegli();
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
