package br.com.example.util;

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

}