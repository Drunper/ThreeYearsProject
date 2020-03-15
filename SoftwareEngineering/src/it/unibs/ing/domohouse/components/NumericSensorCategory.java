package it.unibs.ing.domohouse.components;

import java.util.HashMap;

import it.unibs.ing.domohouse.util.Strings;

public class NumericSensorCategory extends SensorCategory {
	

	private static final long serialVersionUID = -2513977621862706027L;
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
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;

		String domain = infoDomainMap.get(info);
		assert domain.contains(Strings.OPEN_BRACKET) : Strings.DOMAIN_PRECONDITION_1;
		int index = domain.indexOf(Strings.OPEN_BRACKET);
		domain = domain.substring(0, index);
		assert domain.contains(Strings.TO) : Strings.DOMAIN_PRECONDITION_2;
		String [] bounds = domain.split(Strings.TO_WITH_SPACES);
		double [] numericBounds = new double[2];
		numericBounds[0] = Double.parseDouble(bounds[0]);
		numericBounds[1] = Double.parseDouble(bounds[1]);
		
		assert numericBounds != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return numericBounds;
	}
	
	public void putInfo(String detectableInfo, String domain) {
		assert detectableInfo != null;
		assert domain != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = infoDomainMap.size();
		
		infoDomainMap.put(detectableInfo, domain);
		
		assert infoDomainMap.size() >= pre_size;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}

	public String getMeasurementUnit(String info) {
		assert info != null && info.length() > 0;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		String domain = infoDomainMap.get(info);
		assert domain.contains(Strings.OPEN_BRACKET) : Strings.DOMAIN_PRECONDITION_1;
		assert domain.contains(Strings.CLOSED_BRACKET) : Strings.DOMAIN_PRECONDITION_3;
		
		int startIndex = domain.indexOf(Strings.OPEN_BRACKET);
		int endIndex = domain.indexOf(Strings.CLOSED_BRACKET);
		
		String measurementUnit = domain.substring(startIndex + 1, endIndex);
		
		assert measurementUnit != null; 
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return measurementUnit;
	}
	
	public String [] getDetectableInfoList() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return infoDomainMap.keySet().toArray(new String[0]);
	}

	public String toString() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		String result = getName()+Strings.SEPARATOR+getDescr()+Strings.SEPARATOR+String.join(Strings.SEPARATOR, getDetectableInfoList());
		
		assert result != null && result.length() > 0;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return result;
	}
	
	
	private boolean sensorCategoryInvariant() {
		return infoDomainMap != null;
	}
}
