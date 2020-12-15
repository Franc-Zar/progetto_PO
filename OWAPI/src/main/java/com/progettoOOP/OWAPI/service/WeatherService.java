package com.progettoOOP.OWAPI.service;

import java.util.Vector;

import com.progettoOOP.OWAPI.model.AbstractCityData;


public interface WeatherService {

	public abstract Vector<AbstractCityData> actualService(double lat,double lon,int cnt);
 
	public abstract Vector<AbstractCityData> statService
		(int period, double lat, double lon, int cnt, String type);

}

