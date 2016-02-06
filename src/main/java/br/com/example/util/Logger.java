package br.com.example.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by rondymesquita on 27/08/2015
 *
 */
public class Logger {
	
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("Logger.class");
	private static final String PREFIX = "===>";
	private static final String SUFFIX = "<===";
	
	public static void logInfo(String msg){
		logger.log(Level.INFO, String.format("%s %s %s", PREFIX, msg, SUFFIX));
	}
	
	public static void logSevere(String msg){
		logger.log(Level.SEVERE, String.format("%s %s %s", PREFIX, msg, SUFFIX));
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("test-assets/public/index.html");
		int i = 0;
		System.out.println(i++ +": "+ file.getAbsolutePath());
		System.out.println(i++ +": "+ file.getAbsoluteFile());
		System.out.println(i++ +": "+ file.getCanonicalPath());
		System.out.println(i++ +": "+ file.getName());
		System.out.println(i++ +": "+ file.getParent());
		System.out.println(i++ +": "+ file.getParentFile());
		System.out.println(i++ +": "+ file.getPath());
		
	}

}