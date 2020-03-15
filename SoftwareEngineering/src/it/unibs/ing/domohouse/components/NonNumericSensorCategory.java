package it.unibs.ing.domohouse.components;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.domohouse.util.Strings;

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
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String> domain = infoDomainMap.get(info);	
		return domain.toArray(new String[0]);
	}
	
	public boolean contains(String variableName, String valueFromObject) {
		assert variableName != null && valueFromObject != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return infoDomainMap.get(variableName).contains(valueFromObject);
	}
	
	public String[] getDetectableInfoList() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return infoDomainMap.keySet().toArray(new String[0]);
	}
	
	public void putInfo(String detectableInfo, ArrayList<String> domain) {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		assert detectableInfo != null;
		assert domain != null;
		int pre_size = infoDomainMap.size();
		
		infoDomainMap.put(detectableInfo, domain);
		
		assert infoDomainMap.size() >= pre_size;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String toString(){	
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		String result = getName() + Strings.SEPARATOR + getDescr() + Strings.SEPARATOR + String.join(Strings.SEPARATOR, getDetectableInfoList());	
		
		assert result != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	private boolean sensorCategoryInvariant() {
		return infoDomainMap != null;
	}
}
