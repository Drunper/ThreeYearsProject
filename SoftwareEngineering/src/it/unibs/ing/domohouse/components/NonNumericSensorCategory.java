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
		ArrayList<String> domain = infoDomainMap.get(info);	
		return domain.toArray(new String[0]);
	}
	
	
	public String[] getDetectableInfoList() {
		return infoDomainMap.keySet().toArray(new String[0]);
	}
	
	public void putInfo(String detectableInfo, ArrayList<String> domain) {
		assert detectableInfo != null;
		assert domain != null;
		int pre_size = infoDomainMap.size();
		
		infoDomainMap.put(detectableInfo, domain);
		
		assert infoDomainMap.size() >= pre_size;
	}
	
	public String toString(){	
		String result = getName()+':'+getDescr()+':'+String.join(":", getDetectableInfoList());	
		return result;
	}
	
}
