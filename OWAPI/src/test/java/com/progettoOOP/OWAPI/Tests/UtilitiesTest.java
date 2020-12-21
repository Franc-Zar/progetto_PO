package com.progettoOOP.OWAPI.Tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.progettoOOP.OWAPI.util.FileUtilities;

class UtilitiesTest {

	@BeforeEach
	void setUp() {
	}
	
	@AfterEach
	void breakDown() {}

	@Test
	void getSiteContentTest() {
		assertEquals("",FileUtilities.getSiteContent("http://MalformedURL"));		
	}

	@Test
	void getFileContentTest() {
		assertEquals("",FileUtilities.getFileContent("src/main/resources/fileNonPresente.txt"));
		assertNotEquals("",FileUtilities.getFileContent("src/main/resources/elencoCitta.txt"));
	}

}
