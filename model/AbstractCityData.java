package progetto.demoSpringBootApp.model;

public abstract class AbstractCityData {
	
	private double lon,lat;
	private String name; 

	public AbstractCityData(double lon, double lat, String name) {
		
		this.lon=lon;
		this.lat=lat;
		this.name=name;
	
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
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
