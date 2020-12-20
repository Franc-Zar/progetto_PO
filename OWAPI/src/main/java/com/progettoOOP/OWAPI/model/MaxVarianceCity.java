package com.progettoOOP.OWAPI.model;

/** @author Francesco Zaritto
 * 
 * classe public rappresentante all'utente la città che, dopo il calcolo delle statistiche, presenta la 
 * massima varianza del parametro cloud/pressure
 * 
 * costruttore:
 * @param lat
 * @param lon
 * @param name
 * @param maxVariance
 */

public class MaxVarianceCity extends AbstractCityData{
	
	private double maxVariance;

	public MaxVarianceCity(double lat, double lon, String name,double maxVariance) {
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
