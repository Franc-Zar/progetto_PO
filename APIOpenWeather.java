package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.lang.IllegalArgumentException;

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
	public Vector<AbstractCityData> fillCityDataArray(int period,double lon,double lat, int cnt,String info) throws IllegalArgumentException, IOException {
		
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
		else site="http://localhost:8080/archive";
	
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
			
	if(info=="actual") {
			
				 obj = new JSONObject(json);
			  data = obj.getJSONArray("list"); 
		
			//chiamata al metodo che popola la struttura dati
			parseData(data, filteredData);
		
		 }  
			
		
		if(info=="pressureStats" || info=="cloudStats") {
		
			data = new JSONArray(json);
					
			calculateStats(filteredData,data,period,info);

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
	
	
	
	
	private Vector<AbstractCityData>calculateStats(Vector<AbstractCityData> filteredData,JSONArray data,int period,String nomeParam1){
		
		Vector<String> nomi = new Vector <String>();
		Vector<Integer> contatori = new Vector<Integer>();
		CityDataStats singleCity ;
		
		nomi.add(data.getJSONObject(0).getString("name"));
		double lat=data.getJSONObject(0).getDouble("lat");
		double lon=data.getJSONObject(0).getDouble("lon");
		int param1=data.getJSONObject(0).getInt(nomeParam1);
		
		filteredData.add(new CityDataStats(lat,lon,nomi.get(0),param1,Math.pow(param1/100,2)));
		contatori.add(1);
		
		for(int i=1;i<data.length();i++) {
			
				JSONObject o = (JSONObject) data.get(i);
				
				for(int j=0;j<nomi.size();j++) {
					
					if(o.getString("name")==nomi.get(j)) {
						
						if(contatori.get(j)==period) break;
						
						singleCity = (CityDataStats) filteredData.get(j);
						
				singleCity.setParam1Average(singleCity.getParam1Average() + Math.pow(o.getInt(nomeParam1),2));
				singleCity.setParam1Variance(singleCity.getParam1Variance()+ Math.pow(o.getInt(nomeParam1),2));
						contatori.set(j,contatori.get(j)+1);
						
						break;
					}
					else {
						 lat=data.getJSONObject(0).getDouble("lat");
						 lon=data.getJSONObject(0).getDouble("lon");
						 param1=data.getJSONObject(0).getInt(nomeParam1);
						nomi.add(o.getString("name"));
						filteredData.add(new CityDataStats(lat,lon,o.getString("name"),param1,Math.pow(param1/100,2)));
						contatori.add(1);
					}
				}
		}
		for(AbstractCityData x: filteredData) {
			((CityDataStats)x).setParam1Average(((CityDataStats) x).getParam1Average()/period);
			((CityDataStats)x).setParam1Variance((((CityDataStats)x).getParam1Variance()/period) - Math.pow((((CityDataStats)x).getParam1Average())/100,2));
		}
		return filteredData;
		

}

	
}
