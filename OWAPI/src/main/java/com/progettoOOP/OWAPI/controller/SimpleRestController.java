package com.progettoOOP.OWAPI.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.RequestBodyClass;
import com.progettoOOP.OWAPI.service.Archive;
import com.progettoOOP.OWAPI.service.WeatherServiceImp;



/**
 *@author Luigi Smargiassi 
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
	
/**
*il seguente Path restituisce all'utente un breve recap delle funzionalità dell'applicazione a sua 
* disposizione 
*/
		@RequestMapping("/")
	public String home() {
		return "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<body>\r\n" + 
				"<h1>\t--HOME--</h1>\r\n" + 
				"<h3>\"\\actual\" con latitudine,longitudine e numero di località da studiare come parametri,per ottenere informazioni riguardo il meteo corrente</h3>\r\n" + 
				"<h3>\"\\stats\\{type}\\{period}\", come sopra,ma fornendo con period,il numero di giorni da considerare,e con type,il dato fra cloud e pressure che si vuole studiare </h3>\r\n" +
				"</body>\r\n" + 
				"</html>";
	}
	
	
/**
 *il seguente Path prende come parametri le coordinate della città (lat,lon) e il numero di ulteriori città 
 * da analizzare e richiama le funzionalità atte a fornire le informazioni attuali riguardo nuvolosità e pressione
 * @param lat 
 * @param lon
 * @param cnt
 * 
 * @return informazioni attuali
 */
	@RequestMapping(value = "/actual", method = RequestMethod.GET)
	public ArrayList<AbstractCityData> actualWeather(@RequestParam(name="lat",defaultValue="42.12")double lat,
												 @RequestParam(name="lon",defaultValue="14.71")double lon,
												 @RequestParam(name="cnt",defaultValue="1")int cnt){
		return service.actualService(lat, lon, cnt);
	}
	
	
/**
* Il seguente Path prende come corpo della richiesta un oggetto della classe RequestBodyClass sel package model,
* i cui attributi sono le coordinate della città scelta (lat,lon) e l'eventuale numero di ulteriori città da 
* analizzare (cnt), il tipo di parametro (type) e il numero di giorni sul quale eseguire le statistiche (period).
* Richiama le funzionalità di calcollo di media e varianza del type scelto
*  
*  @param type
*  @param period
* 
*  @return statistiche filtrate 
*/
	@PostMapping("/stats/{type}/{period}")
	public List<AbstractCityData> statsWeather(@PathVariable(name="type",required=true)String type,
						   @PathVariable(name="period",required=true)int period, 
						   @RequestBody RequestBodyClass body){
		return service.statService(period, body.getLat(), body.getLon(), body.getCnt(), type);
	}
	
	
	
/** il seguente Path prende come parametri le coordinate della città scelta (lat,lon), l'eventuale 
 * numero di ulteriori città (cnt) e il numero di giorni da selezionare nell'archivio (period). 
 * Mostra all'utente i valori di nuvolosità e pressione per il numero di giornate selezionate per ciascuna delle
 * "cnt" città
 * 
 * @param period
 * @param lat 
 * @param lon
 * @param cnt
 * 
 * @return porzioni di archivio
 */
	@GetMapping("/archive/{period}")
	public List<Object> putArchive(@PathVariable(name="period",required=true) int period,
							 @RequestParam(name="lat",defaultValue="42.12") double lat,
							 @RequestParam(name="lon",defaultValue="14.71") double lon,
							 @RequestParam(name="cnt",defaultValue="1")int cnt) {
		return archive.archiveCall(lat, lon, cnt, period);
	}
	
	
/** Il seguente Path restituisce all'utente la lista delle città monitorate, al fine di generare statistiche, 
 * dall'applicazione
 */
	@GetMapping("/monitored")
	public ArrayList<String> getMonitoredCities() {
		
		return archive.cityListCall();
		
	}
	
}
