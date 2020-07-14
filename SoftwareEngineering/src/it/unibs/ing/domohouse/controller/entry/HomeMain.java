package it.unibs.ing.domohouse.controller.entry;

import java.io.PrintWriter;
import java.util.Scanner;

import it.unibs.ing.domohouse.controller.modules.MasterController;

public class HomeMain {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		input.useDelimiter(System.getProperty("line.separator"));
		PrintWriter output = new PrintWriter(System.out, true);
		MasterController controller = new MasterController(input, output);
		controller.start();
	}
}
