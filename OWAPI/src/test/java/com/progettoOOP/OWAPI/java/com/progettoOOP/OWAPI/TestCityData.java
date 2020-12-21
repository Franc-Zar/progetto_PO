package com.progettoOOP.OWAPI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.progettoOOP.OWAPI.model.CityDataStats;

class TestCityData {
	CityDataStats a=null;
	CityDataStats b=null;
	@BeforeEach
	void setUp() {
		a=new CityDataStats(0,0,"",0,0);
		b=new CityDataStats(0,0,"",0,0);
	}
	
	@AfterEach
	void tearDown() {}
	
	@Test
	@DisplayName("Test dei setter")
	void setterTest() {
		a.setLat(1.00);b.setLat(1.00);
		assertEquals(a.getLat(),b.getLat());
	}
	
	@Test
	 @DisplayName("Test del compareTo")
	void compareToTest() {
		
		assertThrows(ClassCastException.class,()->{
			a.compareTo("Oggetto diverso da CityDataStats");
			});
		assertEquals(0,a.compareTo(a));
		b.setName("Diverso");
		assertTrue(a.compareTo(b)==0);
		b.setAverage(1);
		assertEquals(((Double)0.0).compareTo(1.0),a.compareTo(b));
	}

}
