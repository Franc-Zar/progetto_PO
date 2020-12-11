package progetto.demoSpringBootApp.model;


public class CityData extends AbstractCityData{

	private int cloud,pressure;
	
	public CityData(double lon, double lat,String name,int cloud,int pressure) {
		super(lon,lat,name);
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