package it.unibs.ing.softwareengineering;

public class HomeMain {

	public static void main(String[] args) {
		Home testHouse = new Home("testHouse", "a test house");
		HomeRoom cucina = new HomeRoom("cucina", "una cucina");
		HomeRoom bagno = new HomeRoom("bagno", "un bagno");
		HomeSensorCategory tempSens = new HomeSensorCategory("temp","sensori di temperatura");
		HomeSensor termometro = new HomeSensor("t1_termometro", "termometro cucina");
		HomeSensor termometroBagno = new HomeSensor("t2_termometro", "termometro bagno");
		tempSens.addElementToCategory(termometro);
		tempSens.addElementToCategory(termometroBagno);
		

	}
	
	private void userSelectPrompt() {
		System.out.println();
	}

}
