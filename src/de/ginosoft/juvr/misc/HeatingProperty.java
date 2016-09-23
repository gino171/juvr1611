package de.ginosoft.juvr.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HeatingProperty extends Properties{

	public HeatingProperty() throws IOException {
		// Read properties file. 
		super(); 
		//listDir();
		this.load(new FileInputStream("heating.properties")); 
		
	}
	
	public void listDir() {

		File dir = new File("");
		System.out.println("XXXX"+dir.getAbsolutePath());
		File[] files = dir.listFiles();
		if (files != null) { // Erforderliche Berechtigungen etc. sind vorhanden
			for (int i = 0; i < files.length; i++) {
				System.out.print(files[i].getAbsolutePath());
				if (files[i].isDirectory()) {
					System.out.print(" (Ordner)\n");
				}
				else {
					System.out.print(" (Datei)\n");
				}
			}
		}
	}
}
