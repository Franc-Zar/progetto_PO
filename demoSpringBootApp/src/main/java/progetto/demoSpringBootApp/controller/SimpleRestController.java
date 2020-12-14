package progetto.demoSpringBootApp.controller;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import progetto.demoSpringBootApp.model.CityDataExt;
import progetto.demoSpringBootApp.service.WeatherService;

@RestController
public class SimpleRestController {
	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/actual")
	public Vector<CityDataExt> actualWeather(@RequestParam(name="lat",defaultValue="42.1") double lat,
										  @RequestParam(name="lon",defaultValue="14.71") double lon,
										  @RequestParam(name="cnt",defaultValue="1") int cnt){
		return weatherService.actualService(lat,lon,cnt);
	}
	
	@GetMapping("/stats/cloud/{period}")
	public Vector<CityDataExt> statsCloud(@PathVariable (name="period",required=false) int period,
										  @RequestParam(name="lat",defaultValue="42.1") double lat,
										  @RequestParam(name="lon",defaultValue="14.71") double lon,
										  @RequestParam(name="cnt",defaultValue="1") int cnt){
		if(period<1) period=1;
		return weatherService.statService(period,lat,lon,cnt);
		
	}
}
