package com.progettoOOP.OWAPI.model;


public class CityDataStats extends AbstractCityData{

	private double average,variance;

	public CityDataStats(double lat, double lon, String name, double param1Average, double param1Variance) {
		super(lat,lon,name);
		this.average = param1Average;
		this.variance = param1Variance;
	}


	@Override
	public int compareTo(Object o) {
		if(this!=o) {
			if(o instanceof CityDataStats )
				return ((Double)average).compareTo(((CityDataStats) o).getAverage());
			else throw new ClassCastException();
		}
		return 0;
	}
	
	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}
	
}