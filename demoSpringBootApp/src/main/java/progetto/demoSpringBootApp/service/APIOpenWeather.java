package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.lang.Exception;

import org.json.JSONArray;
import org.json.JSONObject;

import progetto.demoSpringBootApp.model.CityDataExt;

public class APIOpenWeather {
	private static final String key="bd0426afbb623d69f91f5c3f70b6613d";

	public Vector<CityDataExt> fillCityDataArray(float lon,float lat, int cnt) throws Exception{
		if(cnt<1||cnt>20) throw new Exception("Contatore delle citta' non correttamente dimensionato");
		if(lon<-180||lon>180) throw new Exception("Longitudine non correttamente dimensionata");
		if(lat<-90||lat>90) throw new Exception("Latitudine non correttamente dimensionata");
		
		Vector<CityDataExt> filteredData = new Vector<CityDataExt>();
		String site="http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lon+"&cnt="+cnt
		                    +"&units=metric&appid="+key;
		try {
			URL url=new URL(site);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			connection.setReadTimeout(10000);

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//StringBuffer text=new StringBuffer("");
			String json="";
			try {
				String appoggio;
				while((appoggio=reader.readLine())!=null) json+=appoggio;
			}catch(IOException e) {e.printStackTrace();}
			JSONObject ogg=new JSONObject(json);
			JSONArray data = ogg.getJSONArray("list");
			parseData(data, filteredData);
		} 
			catch(MalformedURLException e) { e.printStackTrace(); }
			catch(IOException a) { a.printStackTrace(); } 
		
		return filteredData;
	}
	

	private static void parseData (JSONArray jsonData,Vector<CityDataExt> data){
		String name;
		float lon,lat;
		int pressure,clouds;
		
		for(int i=0; i<jsonData.length();i++) {	
			name= ((JSONObject) jsonData.get(i)).getString("name");
			lon=((JSONObject)jsonData.get(i)).getJSONObject("coord").getFloat("lot");
			lat=((JSONObject)jsonData.get(i)).getJSONObject("coord").getFloat("lat");
			pressure=((JSONObject)jsonData.get(i)).getJSONObject("main").getInt("pressure");
			clouds=((JSONObject)jsonData.get(i)).getJSONObject("clouds").getInt("all");
			data.add(new CityDataExt(name,lon,lat,clouds,pressure));
		}
	}
	
}