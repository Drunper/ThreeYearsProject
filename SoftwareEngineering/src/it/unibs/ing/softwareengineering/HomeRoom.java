package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeRoom extends Element{

	private ArrayList<HomeSensorCategory> sensorCategories= new ArrayList<>();
	private ArrayList<HomeActuatorCategory> actuatorCategories= new ArrayList<>();
	private ArrayList<HomeArtifact> artifactList = new ArrayList<>();
	
	public HomeRoom(String name, String descr) {
		super(name, descr);
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public void setDescr(String descr) {
		this.descr = descr;
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

}
