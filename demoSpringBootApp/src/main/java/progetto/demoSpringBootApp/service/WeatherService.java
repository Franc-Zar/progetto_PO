package progetto.demoSpringBootApp.service;

import java.util.Vector;
import progetto.demoSpringBootApp.model.CityDataExt;

public interface WeatherService {

	public abstract Vector<CityDataExt> actualService(double lat,double lon,int cnt);
}
