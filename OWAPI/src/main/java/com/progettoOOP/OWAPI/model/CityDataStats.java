package com.progettoOOP.OWAPI.model;

/** @author Francesco Zaritto
 * classe public rappresentante il tipo di dato sul quale vengono salvate le statistiche calcolate per 
 * nuvolosità/pressione riguardo i dati storici di una città (entro un dato periodo). le statistiche sono contenute
 * negli attributi "average" (media) e "variance" (varianza) 
 * 
 * costruttore:
 * @param lat
 * @param lon
 * @param name
 * @param variance
 * @param average
 */
public class CityDataStats extends AbstractCityData {

	private double average,variance;

	public CityDataStats(double lat, double lon, String name, double average, double variance) {
		super(lat,lon,name);
		this.average = average;
		this.variance = variance;
	}

/** Override del metodo "compareTo" dell'interfaccia Comparable<Object> utilizzato per ordinare in funzione 
 * della media (average) la nuvolosità/pressione
 * 
 * @param o
 */
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
