package de.ginosoft.juvr.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HeatingProperty extends Properties{

	public HeatingProperty() throws IOException {
		// Read properties file. 
		super(); 
		this.load(new FileInputStream("heating.properties")); 
		
	}
	
}
