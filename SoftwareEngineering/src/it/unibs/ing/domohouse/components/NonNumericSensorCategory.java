package it.unibs.ing.domohouse.components;

import java.util.ArrayList;
import java.util.HashMap;

public class NonNumericSensorCategory extends SensorCategory {

	private static final long serialVersionUID = -7026942869440744184L;
	
	private HashMap<String, ArrayList<String>> infoDomainMap;
	
	public NonNumericSensorCategory(String name, String text) {
		super(name, text);
		infoDomainMap = new HashMap<>();
		setIsNumeric(false);
	}
	
	public String[] getDomain(String info) {
		assert info != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		ArrayList<String> domain = infoDomainMap.get(info);	
		return domain.toArray(new String[0]);
	}
	
	public boolean contains(String variableName, String valueFromObject) {
		assert variableName != null && valueFromObject != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return infoDomainMap.get(variableName).contains(valueFromObject);
	}
	
	public String[] getDetectableInfoList() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return infoDomainMap.keySet().toArray(new String[0]);
	}
	
	public void putInfo(String detectableInfo, ArrayList<String> domain) {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		assert detectableInfo != null;
		assert domain != null;
		int pre_size = infoDomainMap.size();
		
		infoDomainMap.put(detectableInfo, domain);
		
		assert infoDomainMap.size() >= pre_size;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String toString(){	
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		String result = getName()+':'+getDescr()+':'+String.join(":", getDetectableInfoList());	
		
		assert result != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return result;
	}
	
	private boolean sensorCategoryInvariant() {
		boolean checkMap = infoDomainMap != null;
		if(checkMap) return true;
		return false;
	}
	
}
