package it.unibs.ing.domohouse.util;

public class DataOutput {

	public static void printHousingUnit(String housingUnitText) {
		String [] splitted = housingUnitText.split(":");
		System.out.println("Unità immobiliare");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Descrizione: "+splitted[1]);
		System.out.println("Sono presenti le seguenti stanze:");
		for(int i = 2; i < splitted.length; i++) {
			System.out.println(splitted[i]);
		}
	}
	
	public static void printRoom(String roomText) {
		String [] splitted = roomText.split(":");
		System.out.println("Stanza");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Descrizione: "+splitted[1]);
		System.out.println("Nella stanza sono presenti i seguenti elementi:");
		for(int i = 2; i < splitted.length; i++) {
			System.out.println(splitted[i]);
		}
	}
	
	public static void printArtifact(String artifactText) {
		String [] splitted = artifactText.split(":");
		System.out.println("Artefatto");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Descrizione: "+splitted[1]);
	}
	
	public static void printActuator(String actuatorText) {
		String [] splitted = actuatorText.split(":");
		System.out.println("Attuatore");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Categoria: "+splitted[1]);
		System.out.println("Artefatto controllato/i:");
		int i = 2;
		while(splitted[i].equalsIgnoreCase("ac"))
		{
			i++;
			System.out.println(splitted[i++]);
		}
		i++;
		System.out.println("Modalità operativa: "+splitted[i++]);
		System.out.println("Stato: "+splitted[i]);
	}
	
	public static void printSensor(String sensorText) {
		String [] splitted = sensorText.split(":");
		System.out.println("Sensore");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Categoria: "+splitted[1]);
		System.out.println("Oggetto misurato/i:");
		int i = 2;
		while(splitted[i].equalsIgnoreCase("om"))
		{
			i++;
			System.out.println(splitted[i++]);
		}
		i++;
		while(splitted[i].equalsIgnoreCase("vm"))
		{
			i++;
			System.out.println("Ultimo valore misurato: "+splitted[i++]);
		}
	}
	
	public static void printListOfString(String [] list) {
		for(String element : list)
			System.out.println(element);
	}
	
	public static void printSensorCategory(String sensorCategoryText) {
		String [] splitted = sensorCategoryText.split(":");
		System.out.println("Nome: "+splitted[0]);
		System.out.println("Sigla: "+splitted[1]);
		System.out.println("Costruttore: "+splitted[2]);
		int i = 3;
		do
		{
			int k = i - 2;
			System.out.println("Dominio_info_"+k+": "+splitted[i]);
			i++;
		}
		while(!splitted[i].equalsIgnoreCase("endD"));
		System.out.println("Informazioni rilevabili:");
		for(i++; i < splitted.length; i++)
		{
			System.out.println(splitted[i]);
		}
	}
	
	public static void printActuatorCategory(String actuatorCategoryText) {
		String [] splitted = actuatorCategoryText.split(":");
		System.out.println("Sigla: "+splitted[0]);
		System.out.println("Costruttore: "+splitted[1]);
		System.out.println("Modalità di default: "+splitted[2]);
		System.out.println("Modalità operative:");
		for(int i = 3; i < splitted.length; i++)
		{
			System.out.println(splitted[i]);
		}
	}
}
