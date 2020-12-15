package com.progettoOOP.OWAPI.service;

import java.util.Vector;

import org.springframework.stereotype.Service;

import com.progettoOOP.OWAPI.model.AbstractCityData;

@Service
public class WeatherServiceImp implements WeatherService {

	private OpenWeather ow=new OpenWeather();
	
	public Vector<AbstractCityData> actualService(double lat, double lon, int cnt) {
		try {
			ow.APIcall(lat, lon, 0, cnt, "actual");
		}catch (Exception e){e.printStackTrace();}
		
		//pensarci in fase di costruzione package delle eccezioni
		return null;
	}

	public Vector<AbstractCityData> statService(int period, double lat, double lon, int cnt, String type) {
		try {
			ow.APIcall(lat, lon, period, cnt, type);
		}catch (Exception e) {e.printStackTrace();}
		return null;
	}

}
