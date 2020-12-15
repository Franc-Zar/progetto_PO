package com.progettoOOP.OWAPI.model;


public class CityData extends AbstractCityData{

	private int cloud,pressure;
	
	public CityData(double lat, double lon,String name,int cloud,int pressure) {
		super(lat,lon,name);
		this.cloud = cloud;
		this.pressure = pressure;
	}

	public int getCloud() {
		return cloud;
	}

	public void setCloud(int cloud) {
		this.cloud = cloud;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	@Override
	public int compareTo(Object o) {
		
		return 0;
	}
		
}