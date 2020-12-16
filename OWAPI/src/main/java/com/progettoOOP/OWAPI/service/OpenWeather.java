package com.progettoOOP.OWAPI.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityData;
import com.progettoOOP.OWAPI.model.CityDataStats;
import com.progettoOOP.OWAPI.model.maxVarianceCity;

/* @Author Francesco Zaritto
 * @Author Luigi Smargiassi 
 * 
 * classe public i cui metodi stabiliscono la connessione all'API o all'Archivio e che, dopo la manipolazione dei
 * dati, li restituisce all'utente nel formato dell'applicazione, in funzione delle richieste effettuate
 */
public class OpenWeather {
	
	//key necessaria per chiamare l'API
	private final String APIkey="bd0426afbb623d69f91f5c3f70b6613d";

/* metodo public che prende in input le coordinate geografiche (lat,lon), il periodo entro il quale definire
 * i dati (period=0 nel caso di informazioni attuali), il numero totale di città da analizzare (cnt = 1 default)
 * e una Stringa contenente il tipo di richiesta (type = "actual" nel caso di informazioni attuali, "type" = 
 * pressure/cloud nel caso di richieste di statistiche). Restituisce un ArrayList contenente le città cercate
 * e le relative informazioni desiderate
 */
	public ArrayList<AbstractCityData> APIcall(double lat,double lon,int period,int cnt,String type){

		if(cnt<1||cnt>20) throw new IllegalArgumentException("ERROR: Invalid cities' number");
		if(lon<-180.0||lon>180.0) throw new IllegalArgumentException("ERROR: Invalid longitude");
		if(lat<-90.0||lat>90.0) throw new IllegalArgumentException("ERROR: Invalid latitude");
		
		ArrayList<AbstractCityData> filteredData = new ArrayList<AbstractCityData>();
		
		String site;
		if(period==0) 
            site="http://api.openweathermap.org/data/2.5/find?lat="+lat
            +"&lon="+lon+"&cnt="+cnt+"&units=metric&appid="+APIkey;
		else site="http://localhost:8080/archive/"+period+"?lat="+lat+"&lon="+lon+"&cnt="+cnt;
		
		String json=getFileContent(site);
		JSONArray data;
		JSONObject obj;

		if(type.equals("actual")) {
			obj = new JSONObject(json);
			data = obj.getJSONArray("list");
			filterData(data, filteredData);
		}
		else if(type.equals("pressure") || type.equals("cloud")) {	
			data = new JSONArray(json);
			getStats(data,filteredData,type,period);
			Arrays.asList(filteredData);
			Collections.sort(filteredData);
			findMaxVariance(filteredData);
			
		}
		else if(type.equals("all")) {
			
			data = new JSONArray(json);
			getStats(data, filteredData, type, period);
			
		}
		else throw new IllegalArgumentException("ERROR: Invalid type string");
		
		return filteredData;
	}
	
	/* metodo private che prende come parametri un JSONArray contenente i dati ottenuti dalla chiamata all'
	 * archivio (data), un ArrayList (filteredData), una stringa contenente il tipo di parametro sul quale
	 * effettuare le statistiche (type = pressure/cloud) e il periodo (period) sul quale calcolare tali statistiche
	 * (media e varianza) 
	 */
	private void getStats(JSONArray data, ArrayList<AbstractCityData> filteredData, String type, int period) {
		
		
	if(type.equals("all")) Filters.calculateStats(data, filteredData, period, "cloud","pressure");
		
	   else Filters.calculateStats(data, filteredData, period, type);
	   
	 
}

/* metodo private che prende come parametro un JSONArray contenente i dati ottenuti dalla chiamata all'API 
 * (data) e un ArrayList (filterdData). Il metodo popola "filteredData" con le città cercate e i 
 * corrispondenti valori di pressione e nuvolosità, filtrati dalla totalità di informazioni fornite dall'API
 */
	private void filterData(JSONArray data, ArrayList<AbstractCityData> filteredData) {
		String name;
		double lon,lat;
		int pressure,clouds;
		
		for(int i=0; i<data.length();i++) {	
			name = data.getJSONObject(i).getString("name");
			lon = data.getJSONObject(i).getJSONObject("coord").getDouble("lon");
			lat = data.getJSONObject(i).getJSONObject("coord").getDouble("lat");
			pressure = data.getJSONObject(i).getJSONObject("main").getInt("pressure");
			clouds = data.getJSONObject(i).getJSONObject("clouds").getInt("all");
			filteredData.add(new CityData(lat,lon,name,clouds,pressure));
		}	
	}


/* metodo private che prende come parametro l'url di una pagina web, apre la connessione e restituisce 
 * il contenuto della pagina sotto formato di Stringa  
 */
	private String getFileContent(String site) {
		HttpURLConnection connection = null;
		try {
			URL url=new URL(site);
        	connection = (HttpURLConnection) url.openConnection();
        	connection.setConnectTimeout(5000);
			connection.connect();
			connection.setReadTimeout(10000);
		}catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException a) { a.printStackTrace(); }
		
		String json="";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));	
			String appoggio;
			while((appoggio=reader.readLine())!=null)
				json+=appoggio;
			reader.close();
		} catch (IOException e) {e.printStackTrace();}
		return json;
	}

	
/* metodo private che prende come parametro una ArrayList di dati ordinati rispetto alla media di 
 * nuvolosità/pressione e la restituisce dopo aver aggiunto, come ultimo elemento, la città che presenta
 * varianza massima di nuvolosità/pressione
 */
	private void findMaxVariance(ArrayList<AbstractCityData> filteredData) {
		
		double maxVariance = ((CityDataStats) filteredData.get(0)).getVariance();
        double lat = ((CityDataStats) filteredData.get(0)).getLat();
	    double lon = ((CityDataStats) filteredData.get(0)).getLon();
	    double newVariance;
	      
        String name = ((CityDataStats) filteredData.get(0)).getName();
		
		for(int i=0; i<filteredData.size(); i++) {
			     
			newVariance = ((CityDataStats) filteredData.get(i)).getVariance();
			
			            if(maxVariance < newVariance) {
			            	
			            	     maxVariance = newVariance;
			            	     name = ((CityDataStats) filteredData.get(i)).getName();
			            	     lat = ((CityDataStats) filteredData.get(i)).getLat();
			            	     lon = ((CityDataStats) filteredData.get(i)).getLon();
			          } }
	
		filteredData.add(new maxVarianceCity(lat,lon,name,maxVariance));
		
		}           	                          
			
	}
	



	
