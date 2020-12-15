package com.progettoOOP.OWAPI.model;

public abstract class AbstractCityData implements Comparable<Object>{
	
	private double lon,lat;
	private String name; 

	public AbstractCityData(double lat, double lon, String name) {	
		this.lon=lon;
		this.lat=lat;
		this.name=name;
	}

	public abstract int compareTo(Object o);
	
	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}