package com.progettoOOP.OWAPI.model;


/** 
 * classe public i cui attributi "cloud" e "pressure" ospitano rispettivamente i valori di nuvolosità e pressione
 * attuali
 * 
 * costruttore:
 * @param lat
 * @param lon
 * @param name
 * @param cloud
 * @param pressure
 *
 * @author Luigi Smargiassi 
 */
public class CityData extends AbstractCityData {

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

	
		
}
