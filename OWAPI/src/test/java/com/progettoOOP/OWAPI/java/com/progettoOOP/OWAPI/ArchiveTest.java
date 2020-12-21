package com.progettoOOP.OWAPI;

import java.util.List;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.progettoOOP.OWAPI.service.Archive;

@SpringBootTest
class ArchiveTest {
	
	Archive a=null;
	
	@BeforeEach
	void setUp() throws Exception{
		a=new Archive();
	}
	
	@AfterEach
	void tearDown() throws Exception{}
	
	
	@Test
	void archiveTest1() {
		
		for(int period=1; period<14;period++)
		assertEquals(((List<Object>)((HashMap) a.archiveCall(42.12, 14.71, 1, period).get(0)).get("data")).size(),period);
	}
	
	@Test
	void archiveTest2() {
		assertEquals("[]",(Object)a.archiveCall(100.00, 14.71, 1, 1).toString(),"lat out of bounds");
		assertEquals("[]",(Object)a.archiveCall(42.12, 100.00, 1, 1).toString(),"lon out of bounds");
		assertEquals("[]",(Object)a.archiveCall(42.12, 14.71, 1, 1000).toString(),"period out of bounds");
	}
}