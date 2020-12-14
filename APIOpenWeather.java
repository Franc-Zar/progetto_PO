package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.lang.IllegalArgumentException;
import java.lang.Math;

import org.json.JSONArray;
import org.json.JSONObject;

import progetto.demoSpringBootApp.model.CityDataStats;
import progetto.demoSpringBootApp.model.AbstractCityData;
import progetto.demoSpringBootApp.model.CityData;

/* @Author Luigi Smargiassi
 * @Author Francesco Zaritto 
 * 
 * Questa classe esegue la chiamata all'API "Current Weather Data" (OpenWeather) e preleva le informazioni
 * desiderate, salvandole in un'opportuna struttura dati
 * */
public class APIOpenWeather {
	
	//chiave di accesso all'API 
	private static final String key="bd0426afbb623d69f91f5c3f70b6613d";

	
	/* Metodo public che prende come parametri le coordinate geografiche (lon,lat) della città richiesta
	 *  e il numero totale di città da analizzare (cnt),effettua la chiamata all'API e restituisce 
	 *  la struttura dati (filteredData) popolata con le informazioni desiderate
	*/
	public Vector<AbstractCityData> fillCityDataArray(int period,double lat,double lon, int cnt,String type) throws IllegalArgumentException, IOException {
		
		if(cnt<1||cnt>20) throw new IllegalArgumentException("ERROR: Invalid cities' number");
		if(lon<-180.0||lon>180.0) throw new IllegalArgumentException("ERROR: Invalid longitude");
		if(lat<-90.0||lat>90.0) throw new IllegalArgumentException("ERROR: Invalid latitude");
		
		
		Vector<AbstractCityData> filteredData = new Vector<AbstractCityData>();
		String site;
		HttpURLConnection connection = null;
		JSONArray data;
		JSONObject obj;
		
		if(period==0) 
		                    //URL per la chiamata all'API 
		       site="http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lon+"&cnt="+cnt
		                    +"&units=metric&appid="+key;
		
		    //URL per la chiamata all'archivio
		else site="http://localhost:8080/archive/"+period+"?lat="+lat+"&lon="+lon+"&cnt="+cnt;
	
		try {
			URL url=new URL(site);
             
			//chiamata all'API (o all'Archivio)
		    connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			connection.setReadTimeout(10000);

			}	catch(MalformedURLException e) { e.printStackTrace(); }
			catch(IOException a) { a.printStackTrace(); } 
		
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		
			String json="";
			
			try {
				String appoggio;
				while((appoggio=reader.readLine())!=null) json+=appoggio;
			}catch(IOException e) {System.out.println("ERROR: I/O error");}
			
	if(type.equals("actual")) {
			
				 obj = new JSONObject(json);
			  data = obj.getJSONArray("list"); 
		
			//chiamata al metodo che popola la struttura dati
			parseData(data, filteredData);
		
		 }  
			
		
		if(type.equals("pressure") || type.equals("cloud")) {
		
			data = new JSONArray(json);
					
			calculateStats(data,filteredData,type,period);

		
		}
		return filteredData;
					
	}
		

	
/* Metodo statico private che prende come parametri un vettore di oggetti JSON (jsonData) e una struttura 
 * dati (data) chiamato dal metodo "fillCityDataArray".
 * Questo metodo restituisce la struttura dati (data) di oggetti "CityDataExt" popolata grazie agli attributi
 * degli oggetti JSON contenuti in jsonData. 
 */
	private static void parseData (JSONArray jsonData, Vector<AbstractCityData> data){
		String name;
		double lon,lat;
		int pressure,clouds;
		
		for(int i=0; i<jsonData.length();i++) {	
			name = jsonData.getJSONObject(i).getString("name");
			lon = jsonData.getJSONObject(i).getJSONObject("coord").getDouble("lon");
			lat = jsonData.getJSONObject(i).getJSONObject("coord").getDouble("lat");
			pressure = jsonData.getJSONObject(i).getJSONObject("main").getInt("pressure");
			clouds = jsonData.getJSONObject(i).getJSONObject("clouds").getInt("all");
			data.add(new CityData(lon,lat,name,clouds,pressure));
		}
		
	}
	
   private static void calculateStats(JSONArray jsonData,Vector<AbstractCityData> filteredData,String type,int period) {
	   
	   String name;
	   double lon,lat;
	   
	   JSONArray stats;
	   JSONObject city;
	   double average=0,variance=0;
	   
	   for(int i=0;i<jsonData.length();i++) {
		   
		   average=0;
		   variance=0;
		   
		  city = jsonData.getJSONObject(i);
		  name = city.getString("name");
		  lon = city.getDouble("lon");
		  lat = city.getDouble("lat");

		  stats = city.getJSONArray("data");  
		     
	                   for(int j=0;j<stats.length(); j++) {
	                	   
	                	   average += stats.getJSONObject(j).getInt(type);
	                       
	                	   variance += Math.pow( stats.getJSONObject(j).getInt(type), 2 );
	            	  
	                   }
	                   
	                   average/=period;
	                   
	                   variance = variance/period - Math.pow(average, 2); 
	                   
	                   filteredData.add(new CityDataStats(lon,lat,name,average,variance));
	                   
	              }
	                 
   }

	
}
