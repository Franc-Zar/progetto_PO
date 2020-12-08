package progetto.demoSpringBootApp.model;

public class CityData{

	private float lon,lat;
	private int cloud,pressure;
	
	public CityData(float lon, float lat,int cloud,int pressure) {
		this.lon = lon;
		this.lat = lat;
		this.cloud = cloud;
		this.pressure = pressure;
	}

	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
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