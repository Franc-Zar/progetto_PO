package com.progettoOOP.OWAPI.model;


/** @author Francesco Zaritto
 * Classe public che rappresenta il tipo di dato contenente le statistiche complete, cioè riguardanti media e
 * varianza sia di pressione che di temperatura
 * 
 * costruttore:
 * @param lat
 * @param lon
 * @param name
 * @param cloudAverage
 * @param cloudVariance
 * @param pressureAverage
 * @param pressureVariance
 */
public class CityDataStatsAll extends AbstractCityData {
	
	
	private double cloudAverage,cloudVariance,pressureAverage,pressureVariance;

	
	public CityDataStatsAll(double lat, double lon, String name, double cloudAverage, double cloudVariance,
			double pressureAverage, double pressureVariance) {
		super(lat, lon, name);
		this.cloudAverage = cloudAverage;
		this.cloudVariance = cloudVariance;
		this.pressureAverage = pressureAverage;
		this.pressureVariance = pressureVariance;
	}

	public double getCloudAverage() {
		return cloudAverage;
	}

	public void setCloudAverage(double cloudAverage) {
		this.cloudAverage = cloudAverage;
	}

	public double getCloudVariance() {
		return cloudVariance;
	}

	public void setCloudVariance(double cloudVariance) {
		this.cloudVariance = cloudVariance;
	}

	public double getPressureAverage() {
		return pressureAverage;
	}

	public void setPressureAverage(double pressureAverage) {
		this.pressureAverage = pressureAverage;
	}

	public double getPressureVariance() {
		return pressureVariance;
	}

	public void setPressureVariance(double pressureVariance) {
		this.pressureVariance = pressureVariance;
	}
}
