package com.progettoOOP.OWAPI.model;

public class RequestBodyClass {
	private double lat;
	private double lon;
	private int cnt;
	
	
	public RequestBodyClass(double lat, double lon, int cnt) {
	
		this.lat = lat;
		this.lon = lon;
		this.cnt = cnt;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
	
	
	
	
	
	
	
	
	
}
