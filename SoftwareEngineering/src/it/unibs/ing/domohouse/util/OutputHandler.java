package it.unibs.ing.domohouse.util;

public class OutputHandler {

	public static void printHousingUnit(String housingUnitText) {
		assert housingUnitText != null;
		assert housingUnitText.contains(Strings.SEPARATOR);
		
		String [] splitted = housingUnitText.split(Strings.SEPARATOR);
		System.out.println(Strings.HOUSING_UNIT);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.DESCRIPTION+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.ROOMS_AVAILABLE);
		for(int i = Strings.THIRD_TOKEN; i < splitted.length; i++) {
			System.out.println(splitted[i]);
		}
	}
	
	public static void printRoom(String roomText) {
		assert roomText != null;
		assert roomText.contains(Strings.SEPARATOR);
		
		String [] splitted = roomText.split(Strings.SEPARATOR);
		System.out.println(Strings.ROOM);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.DESCRIPTION+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.ELEMENTS_AVAILABLE);
		for(int i = Strings.THIRD_TOKEN; i < splitted.length; i++) {
			System.out.println(splitted[i]);
		}
	}
	
	public static void printArtifact(String artifactText) {
		assert artifactText != null;
		assert artifactText.contains(Strings.SEPARATOR);
		
		String [] splitted = artifactText.split(Strings.SEPARATOR);
		System.out.println(Strings.ARTIFACT);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.DESCRIPTION+splitted[Strings.SECOND_TOKEN]);
	}
	
	public static void printActuator(String actuatorText) {
		assert actuatorText != null;
		assert actuatorText.contains(Strings.SEPARATOR);
		
		String [] splitted = actuatorText.split(Strings.SEPARATOR);
		System.out.println(Strings.ACTUATOR);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.CATEGORY+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.CONTROLLED_ELEMENTS);
		int i = Strings.THIRD_TOKEN;
		while(splitted[i].equalsIgnoreCase(Strings.CONTROLLED_ELEMENT_TAG))
		{
			i++;
			System.out.println(splitted[i++]);
		}
		System.out.println(Strings.OPERATING_MODE+splitted[i++]);
		System.out.println(Strings.STATUS+splitted[i]);
	}
	
	public static void printSensor(String sensorText) {
		assert sensorText != null;
		assert sensorText.contains(Strings.SEPARATOR);
		
		String [] splitted = sensorText.split(Strings.SEPARATOR);
		System.out.println(Strings.SENSOR);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.CATEGORY+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.MEASURED_ELEMENTS);
		int i = Strings.THIRD_TOKEN;
		while(splitted[i].equalsIgnoreCase(Strings.MEASURED_ELEMENT_TAG))
		{
			i++;
			System.out.println(splitted[i++]);
		}

		while(splitted[i].equalsIgnoreCase(Strings.MEASURED_VALUE_TAG))
		{
			i++;
			System.out.println(Strings.LAST_MEASURED_VALUE+splitted[i++]);
		}

		System.out.println(Strings.STATUS+splitted[i]);
	}
	
	public static void printListOfString(String [] list) {
		assert list != null;
		
		for(String element : list)
			System.out.println(element);
	}
	
	public static void printSensorCategory(String sensorCategoryText) {
		assert sensorCategoryText != null;
		assert sensorCategoryText.contains(Strings.SEPARATOR);
		
		String [] splitted = sensorCategoryText.split(Strings.SEPARATOR);
		System.out.println(Strings.SENSOR_CATEGORY);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.ABBREVATION+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.MANUFACTURER+splitted[Strings.THIRD_TOKEN]);
		int i = Strings.FOURTH_TOKEN;
		do
		{
			int k = i - 2; // we are starting from the fourth token but it's the first information
			System.out.println(Strings.INFO_DOMAIN+k+Strings.SEPARATOR_WITH_SPACE+splitted[i]);
			i++;
		}
		while(splitted[i].equalsIgnoreCase(Strings.END_DOMAIN_TAG));
		System.out.println(Strings.DETECTABLE_INFOS);
		do{
			System.out.println(splitted[i]);
			i++;
		}while(i < splitted.length);
	}
	
	public static void printActuatorCategory(String actuatorCategoryText) {
		assert actuatorCategoryText != null;
		assert actuatorCategoryText.contains(Strings.SEPARATOR);
		
		String [] splitted = actuatorCategoryText.split(Strings.SEPARATOR);
		System.out.println(Strings.ACTUATOR_CATEGORY);
		System.out.println(Strings.NAME+splitted[Strings.FIRST_TOKEN]);
		System.out.println(Strings.ABBREVATION+splitted[Strings.SECOND_TOKEN]);
		System.out.println(Strings.MANUFACTURER+splitted[Strings.THIRD_TOKEN]);
		System.out.println(Strings.DEFAULT_MODE+splitted[Strings.FOURTH_TOKEN]);
		System.out.println(Strings.OPERATING_MODES);
		for(int i = Strings.FIFTH_TOKEN; i < splitted.length; i++)
		{
			System.out.println(splitted[i]);
		}
	}
	
	/*I know it sucks, but there's no known way to clear the output inside an IDE
	I could have a better way if we ran natively the app into a Windows command prompt,
	but since we run it in Eclipse this is the best way to do it.*/
	public final static void clearOutput()
	{
		for (int i = 0; i < Strings.SPACING_COSTANT; ++i) System.out.println();
	}
}
