package progetto.demoSpringBootApp.model;

public class CityDataStats extends AbstractCityData{

	private double param1Average,param1Variance;

	public CityDataStats(double lon, double lat, String name, double param1Average, double param1Variance) {
		super(lon, lat, name);
		this.param1Average = param1Average;
		this.param1Variance = param1Variance;
	}

	public double getParam1Average() {
		return param1Average;
	}

	public void setParam1Average(double average) {
		this.param1Average = average;
	}

	public double getParam1Variance() {
		return param1Variance;
	}

	public void setParam1Variance(double variance) {
		this.param1Variance = variance;
	}
	
	
	
	
	
	
	
	
}
