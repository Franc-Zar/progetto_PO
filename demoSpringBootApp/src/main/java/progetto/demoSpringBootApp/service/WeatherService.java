package progetto.demoSpringBootApp.service;

import java.util.Vector;
import progetto.demoSpringBootApp.model.CityDataExt;

public interface WeatherService {

	public abstract Vector<CityDataExt> actualService(float lat,float lon,int cnt);
}
