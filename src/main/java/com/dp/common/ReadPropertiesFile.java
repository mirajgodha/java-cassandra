package com.dp.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFile {

	/* Create basic object */
	ClassLoader objClassLoader = null;
	Properties commonProperties = new Properties();
	String fileName = "dev/common.properties";

	public ReadPropertiesFile() {
		/* Initialize 'objClassLoader' once so same object used for multiple files. */
		objClassLoader = this.getClass().getClassLoader();
	}

	public String readKey(String propertiesFilename, String key, String defaultValue) {
		/* Simple validation */
		if (propertiesFilename != null && !propertiesFilename.trim().isEmpty() && key != null
				&& !key.trim().isEmpty()) {
			/* Create an object of FileInputStream */
			FileInputStream objFileInputStream = null;

			/**
			 * Following try-catch is used to support upto 1.6. Use try-with-resource in JDK
			 * 1.7 or above
			 */
			try {
				InputStream is = this.getClass().getResourceAsStream(propertiesFilename);
				if(is != null) 
				    commonProperties.load(is);
				else return defaultValue;
				
//				System.out.println("------" +is);
//				/* Read file from resources folder */
//				objFileInputStream = new FileInputStream(objClassLoader.getResource(propertiesFilename).getFile());
//				/* Load file into commonProperties */
//				commonProperties.load(objFileInputStream);
//				/* Get the value of key */
				return String.valueOf(commonProperties.get(key)) == null ? defaultValue : String.valueOf(commonProperties.get(key)) ;
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				/* Close the resource */
				if (objFileInputStream != null) {
					try {
						objFileInputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return defaultValue;
	}

	public String readKey(String key) {
		return readKey(fileName, key, null);
	}

	public String readKey(String key, String defalutValue) {
		return readKey(fileName, key, defalutValue);
	}

	public static void main(String[] args) {
		/* Create object of ReadResourceFile */
		ReadPropertiesFile objPropertiesFile = new ReadPropertiesFile();

		/* Will give you 'null' in case key not available */
		System.out.println("Database Driver: " + objPropertiesFile.readKey("dbdriver"));

		/* Read values from resource folder */
		System.out.println("Host: " + objPropertiesFile.readKey("host"));
		System.out.println("Username: " + objPropertiesFile.readKey("username"));

		try {
			/* Print the password from commom.properties file */
			System.out.println("Password: " + objPropertiesFile.readKey("password"));

			/*
			 * Put the current thread in sleep for 5 seconds and change the value of
			 * 'password'
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}