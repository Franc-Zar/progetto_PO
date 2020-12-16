package com.progettoOOP.OWAPI.model;

/* @Author Francesco Zaritto
 * 
 * classe public rappresentante all'utente la città che, dopo il calcolo delle statistiche, presenta la 
 * massima varianza del parametro cloud/pressure
 */

public class maxVarianceCity extends AbstractCityData{
	
	private double maxVariance;

	public maxVarianceCity(double lat, double lon, String name,double maxVariance) {
		super(lat, lon, name);
		this.maxVariance=maxVariance;
		
	}

	public double getMaxVariance() {
		return maxVariance;
	}

	public void setMaxVariance(double maxVariance) {
		this.maxVariance = maxVariance;
	}


	
	
	
	
	
	
	

}
