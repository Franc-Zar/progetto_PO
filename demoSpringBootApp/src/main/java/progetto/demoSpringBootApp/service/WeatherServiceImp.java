package progetto.demoSpringBootApp.service;

import java.util.Vector;

import org.springframework.stereotype.Service;

import progetto.demoSpringBootApp.model.CityDataExt;

@Service
public class WeatherServiceImp implements WeatherService {

	public Vector<CityDataExt> actualService(double lat, double lon,int cnt) {
		APIOpenWeather apiCall= new APIOpenWeather();
		try {
		return apiCall.fillCityDataArray(lon, lat, cnt);
		} catch(Exception e) {}
		return null;
	}

	public Vector<CityDataExt> statService(int period, double lat, double lon, int cnt) {
		return null;
	}

}
