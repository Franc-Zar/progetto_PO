package com.progettoOOP.OWAPI.controller;

import java.util.List;
//import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.service.Archive;
import com.progettoOOP.OWAPI.service.WeatherServiceImp;

@RestController
public class SimpleRestController {

	@Autowired
	Archive archive;
	@Autowired
	WeatherServiceImp service;
	
	@RequestMapping("/")
	public String home() {
		String instruction="/actual con parametri lat,lon,cnt per i dati al momento corrente\n";
		instruction+="/stats/{type}/{period} con stessi parametri,type è una stringa corrispondente il dato da studiare,period è un intero dei giorni da considerare";
		return instruction;
	}
	
	@GetMapping("/actual")
	public Vector<AbstractCityData> actualWeather(@RequestParam(name="lat",defaultValue="42.12")double lat,
												 @RequestParam(name="lon",defaultValue="14.71")double lon,
												 @RequestParam(name="cnt",defaultValue="1")int cnt){
		return service.actualService(lat, lon, cnt);
	}
	
	@GetMapping("/stats/{type}/{period}")
	public Vector<AbstractCityData> statsWeather(@PathVariable(name="type",required=true)String type,
												 @PathVariable(name="period",required=true)int period, 
												 @RequestParam(name="lat",defaultValue="42.12")double lat,
												 @RequestParam(name="lon",defaultValue="14.71")double lon,
												 @RequestParam(name="cnt",defaultValue="1")int cnt){
		return service.statService(period, lat, lon, cnt, type);
	}
	
	
	@GetMapping("/archive/{period}")
	public List<Object> putArchive(@PathVariable(name="period",required=true) int period,
							 @RequestParam(name="lat",defaultValue="42.12") double lat,
							 @RequestParam(name="lon",defaultValue="14.71") double lon,
							 @RequestParam(name="cnt",defaultValue="1")int cnt) {
		return archive.archiveCall(lat, lon, cnt, period);
}
}
