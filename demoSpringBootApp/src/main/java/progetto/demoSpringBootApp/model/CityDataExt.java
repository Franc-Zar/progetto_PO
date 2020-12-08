package progetto.demoSpringBootApp.model;

public class CityDataExt extends CityData {

	private String name;

	public CityDataExt(String name,float lon, float lat, int cloud, int pressure) {
		super(lon, lat, cloud, pressure);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
