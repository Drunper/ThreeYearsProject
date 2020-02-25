package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.ing.domohouse.interfaces.Manageable;

public class NumericSensorCategory extends SensorCategory {
	

	private static final long serialVersionUID = -2513977621862706027L;
	//private String name;
	//private String text;
	private HashMap<String, String> infoDomainMap;
	/*
	 * invariante di classe: detectableInfos.size() > 0 && name != null && text != null
	 */
	public NumericSensorCategory(String name, String text) {
		super(name, text);
		infoDomainMap = new HashMap<>();
		setIsNumeric(true);
	}

	public double [] getDomain(String info) {  // info = 1 -to- 20 (gradi)
	
		assert info != null && info.length() > 0;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";

		String domain = infoDomainMap.get(info);
		assert domain.contains("(") : "domain non contiene \"(\" ";
		int Index = domain.indexOf("(");
		domain = domain.substring(0, Index);
		assert domain.contains("-to-") : "info non contiene \"to\" ";
		String [] bounds = domain.split(" -to- ");
		double [] numericBounds = new double[2];
		numericBounds[0] = Double.parseDouble(bounds[0]);
		numericBounds[1] = Double.parseDouble(bounds[1]);
		
		assert numericBounds != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return numericBounds;
	}
	
	// daAdattare nel caso di più informazioni rilevabili
	public void putInfo(String detectableInfo, String domain) {
		assert detectableInfo != null;
		assert domain != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = infoDomainMap.size();
		
		infoDomainMap.put(detectableInfo, domain);
		
		assert infoDomainMap.size() >= pre_size;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}

	public String getMeasurementUnit(String info) {
		assert info != null && info.length() > 0;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		String domain = infoDomainMap.get(info);
		assert domain.contains("(") : "domain non contiene \"(\" ";
		assert domain.contains(")") : "domain non contiene \")\" ";
		
		int startIndex = domain.indexOf("(");
		int endIndex = domain.indexOf(")");
		
		String measurementUnit = domain.substring(startIndex + 1, endIndex);
		
		assert measurementUnit != null; 
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return measurementUnit;
	}
	
	public String [] getDetectableInfoList() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return infoDomainMap.keySet().toArray(new String[0]);
	}

	public String toString() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		String result = getName()+':'+getDescr()+':'+String.join(":", getDetectableInfoList());
		
		assert result != null && result.length() > 0;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return result;
	}
	
	
	private boolean sensorCategoryInvariant() {
		boolean checkMap = infoDomainMap != null;
		if(checkMap) return true;
		return false;
	}
}
