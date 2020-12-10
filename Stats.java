package progetto.demoSpringBootApp.service;

import java.util.Vector;
import java.lang.Math.pow;

import org.json.JSONArray;
import org.json.JSONObject;

public class Stats {

	public calculateStats(JSONArray data,int period,String nomeParam1){
		String nomeParam2;
		if(nomeParam1.equals("cloud")) nomeParam2="pressure";
		else nomeParam2="cloud";
		
		Vector<CityDataStats> filteredData;
		Vector<String> nomi;
		Vector<Integer> contatori;
		
		nomi.add(data.getJSONObject(0).getString("name"));
		double lat=data.getJSONObject(0).getDouble("lat");
		double lon=data.getJSONObject(0).getDouble("lon");
		int param1=data.getJSONObject(0).getInt(nomeParam1);
		//int param2=data.get(0).getInt(param2);
		filteredData.add(new CityDataStats(lat,lon,nomi.get(0),param1,Math.pow(param1/100,2)));
		contatori.add(1);
		
		for(int i=1;i<data.length();i++) {
			
				JSONObject o=data.get(i);
				for(int j=0;j<nomi.length();j++) {
					if(o.getString("name")==nomi.get(j)) {
						if(contatori.get(j)==period) break;
						filteredData.get(j).setParam1Average(filteredData.get(j).getParam1Average()+Math.pow(o.getInt(nomeParam1),2));
						filteredData.get(j).setParam1Variance(filteredData.get(j).getParam1Variance()+Math.pow(o.getInt(nomeParam1),2));
						contatori.get(j)+=1;
						//filteredData.get(j).setParam2(filteredData.get(j).getParam2()+o.getInt(nomeParam2));
						break;
					}
					else {
						double lat=data.getJOSNObject(0).getDouble("lat");
						double lon=data.getJOSNObject(0).getDouble("lon");
						int param1=data.getJOSNObject(0).getInt(param1);
						nomi.add(o.getString("name"));
						filteredData.add(new CityDataStats(lat,lon,o.getString("name"),param1,Math.pow(param1/100,2));
						contatori.add(1);
					}
				}
		}
		for(CityDataStats x: filteredData) {
			x.setParam1Average(x.getParam1Average()/period)
		}
		
	}
}
