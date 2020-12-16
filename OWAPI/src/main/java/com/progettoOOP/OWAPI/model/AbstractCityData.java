package com.progettoOOP.OWAPI.model;

/* @Author Luigi Smargiassi
 * classe astratta public caratterizzata dagli attributi fondamentali che definiscono ogni città nell'applicazione:
 * nome (name)
 * longitudine (lon)
 * latitudine (lat)
 */
public abstract class AbstractCityData implements Comparable<Object>{
	
	private double lon,lat;
	private String name; 

	public AbstractCityData(double lat, double lon, String name) {	
		this.lon=lon;
		this.lat=lat;
		this.name=name;
	}

	
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
	
	@Override
	public int compareTo(Object o) {
		
		return 0;
	}
}