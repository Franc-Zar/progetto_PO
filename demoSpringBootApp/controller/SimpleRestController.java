package progetto.demoSpringBootApp.controller;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	/*
	@GetMapping("/stats/cloud/{period}")
	public Vector<CityDataExt> statsCloud(@PathVariable (name="period",defalutValue=7)
										  @RequestParam("lat") float lat,
										  @RequestParam("lon") float lon,
										  @RequestParam("cnt") int cnt){
		
	}
	
	@GetMapping("/stats/pressure")
	/*
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public CityData example1(@RequestParam(name="name",defaultValue="Enzino") String param1) {
		return new CityData(param1,"Iacchetti");
	}
	@GetMapping("/hello/{param1}")
	public CityData example3(@PathVariable String param1) {
		return new CityData(param1,"Iacchetti");
	}
	
	@PostMapping("/hello/{param1}")
	public CityData example2(@PathVariable String param1,@RequestBody String body) {
		return new CityData(param1,body);
	}
	
	*/
}
