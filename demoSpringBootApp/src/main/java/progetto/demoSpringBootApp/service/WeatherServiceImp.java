package progetto.demoSpringBootApp.service;

import java.util.Vector;

import org.springframework.stereotype.Service;

import progetto.demoSpringBootApp.model.CityDataExt;

@Service
public class WeatherServiceImp implements WeatherService {

	//private final String APIkey =""; 
	public Vector<CityDataExt> actualService(float lat, float lon,int cnt) {
		try {
		APIOpenWeather apiCall= new APIOpenWeather();
		return apiCall.fillCityDataArray(lon, lat, cnt);
		} catch(Exception e) {}
		return null;
	}

}
