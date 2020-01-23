package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeLogin {
	
	//loginType 0 is a normal user
	//loginType 1 is maintenance mode
	
	private String mantPass = "123456";
	private String userName;
	private int loginType;
	
	
	public HomeLogin(String user) {
		this.userName = user;
		loginType = 0;
	}
	
	public HomeLogin(String user, String pass) {
		this.userName = user;
		//this will check the password and if correct will set the loginType to 1 
		do {
			if (checkPass(pass)) {
			loginType = 1;
			break;
			}
			else {
				Scanner s = new Scanner(System.in);
				pass = s.next();
			}
		}
		while(true);
		
	}
	
	private boolean checkPass(String pass){
		return pass.equals(mantPass);
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public int getLoginType() {
		return this.loginType;
	}
	
}
