package progetto.demoSpringBootApp.model;

public class CityData{

	private double lon,lat;
	private int cloud,pressure;
	
	public CityData(double lon, double lat,int cloud,int pressure) {
		this.lon = lon;
		this.lat = lat;
		this.cloud = cloud;
		this.pressure = pressure;
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
	}public int getCloud() {
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