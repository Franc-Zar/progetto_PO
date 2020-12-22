package com.progettoOOP.OWAPI.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityData;


/**classe che implementa l'interfaccia public "WeatherService" 
 * 
 *@author Luigi Smargiassi
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
	//@Override
	public ArrayList<AbstractCityData> actualService(double lat, double lon, int cnt) {
		ArrayList<AbstractCityData> a=new ArrayList<>();
		try {
			a=OpenWeather.APIcall(lat, lon, 0, cnt, "actual");
			if(a.isEmpty()) a.add(
					new CityData(0,0,"ERROR:per ulteriori informazioni,controllare la console",0,0)
					); 
		}catch (IllegalArgumentException e){
			e.printStackTrace();
			a.add(new CityData(0,0,e.getMessage(),0,0));
			}
		return a;
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
	//@Override
	public ArrayList<AbstractCityData> statService(int period, double lat, double lon, int cnt, String type) {
		ArrayList<AbstractCityData> a= new ArrayList<>();
		try {
			a=OpenWeather.APIcall(lat, lon, period, cnt, type);
			if(a.isEmpty()) a.add(
				new CityData(0,0,"ERROR:per ulteriori informazioni,controllare la console",0,0)
				); 
		}catch (IllegalArgumentException e){
				e.printStackTrace();
				a.add(new CityData(0,0,e.getMessage(),0,0));
				}
			return a;
	}

}
