package com.progettoOOP.OWAPI.controller;

import java.util.List;
//import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.service.Archive;
import com.progettoOOP.OWAPI.service.WeatherServiceImp;



/* @Author Luigi Smargiassi 
 * 
 * controller dell'applicazione: sono definite in esso i Path richiamabili dall'utente per usufruire delle 
 * diverse funzionalità
 */
@RestController
public class SimpleRestController {

	@Autowired
	Archive archive;
	@Autowired
	WeatherServiceImp service;
	
/* il seguente Path restituisce all'utente un breve recap delle funzionalità dell'applicazione a sua 
* disposizione 
*/
	@RequestMapping("/")
	public String home() {
		String instruction="/actual con parametri lat,lon,cnt per i dati al momento corrente\n";
		instruction+="/stats/{type}/{period} con parametri lat,lon,cnt: type è una stringa corrispondente il dato da studiare,period è un intero dei giorni da considerare";
		return instruction;
	}
	
	
/* il seguente Path prende come parametri le coordinate della città (lat,lon) e il numero di ulteriori città 
 * da analizzare e richiama le funzionalità atte a fornire le informazioni attuali riguardo nuvolosità e pressione
 */
	@GetMapping("/actual")
	public ArrayList<AbstractCityData> actualWeather(@RequestParam(name="lat",defaultValue="42.12")double lat,
												 @RequestParam(name="lon",defaultValue="14.71")double lon,
												 @RequestParam(name="cnt",defaultValue="1")int cnt){
		return service.actualService(lat, lon, cnt);
	}
	
	
/* il seguente Path prende come parametri le coordinate della città scelta (lat,lon) e l'eventuale 
*  numero di ulteriori città da analizzare (cnt), il tipo di parametro (type) e il numero di giorni sul quale 
*  eseguire le statistiche (period). Richiama le funzionalità di calcollo di media e varianza del type scelto
*/
	@GetMapping("/stats/{type}/{period}")
	public ArrayList<AbstractCityData> statsWeather(@PathVariable(name="type",required=true)String type,
												 @PathVariable(name="period",required=true)int period, 
												 @RequestParam(name="lat",defaultValue="42.12")double lat,
												 @RequestParam(name="lon",defaultValue="14.71")double lon,
												 @RequestParam(name="cnt",defaultValue="1")int cnt){
		return service.statService(period, lat, lon, cnt, type);
	}
	
	
	
/* il seguente Path prende come parametri le coordinate della città scelta (lat,lon), l'eventuale 
 * numero di ulteriori città (cnt) e il numero di giorni da selezionare nell'archivio (period). 
 * Mostra all'utente i valori di nuvolosità e pressione per il numero di giornate selezionate per ciascuna delle
 * "cnt" città
 */
	@GetMapping("/archive/{period}")
	public List<Object> putArchive(@PathVariable(name="period",required=true) int period,
							 @RequestParam(name="lat",defaultValue="42.12") double lat,
							 @RequestParam(name="lon",defaultValue="14.71") double lon,
							 @RequestParam(name="cnt",defaultValue="1")int cnt) {
		return archive.archiveCall(lat, lon, cnt, period);
}
}
