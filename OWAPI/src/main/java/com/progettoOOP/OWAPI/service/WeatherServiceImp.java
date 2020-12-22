package com.progettoOOP.OWAPI.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.progettoOOP.OWAPI.model.AbstractCityData;

/** @author Luigi Smargiassi
 * 
 * classe che implementa l'interfaccia public "WeatherService" 
 */
@Service
public class WeatherServiceImp implements WeatherService {
	
	
/** metodo public che prende come parametri le coordinate della città (lat,lon) e il numero cnt di città da analizzare.
 * Richiama le funzionalità di "APIcall", richiedendo informazioni attuali riguardo nuvolosità e pressione, e 
 * restituendole all'utente	 
 * 
 * @param lat
 * @param lon
 * @param cnt
 * 
 * @return dati forniti da OpenWeather.APIcall
 */
	
	public ArrayList<AbstractCityData> actualService(double lat, double lon, int cnt) {
		try {
			return OpenWeather.APIcall(lat, lon, 0, cnt, "actual");
		}catch (IllegalArgumentException e){e.printStackTrace();}
		
		return null;
	}
	
	
	/** metodo public che prende come parametri le coordinate della città (lat,lon) e il numero (cnt) di città da analizzare,
	 * il periodo sul quale calcolare le statistiche (period) e una stringa contenente il parametro sul quale si 
	 * vogliono eseguire tali statistiche (type = "cloud"/"pressure")
	 * Richiama le funzionalità di "APIcall", richiedendo informazioni dallo storico riguardo nuvolosità/pressione,
	 * entro il periodo specificato, ne calcola media e varianza e le restituisce all'utente	
	 * 
	 * @param period
     * @param lat
     * @param lon
     * @param cnt
     * @param type
     * 
     * @return dati forniti da OpenWeather.APIcall
	 */
	
	public ArrayList<AbstractCityData> statService(int period, double lat, double lon, int cnt, String type) {
		try {
			 return OpenWeather.APIcall(lat, lon, period, cnt, type);
		}catch (IllegalArgumentException e) {e.printStackTrace();}
		return null;
	}

}
