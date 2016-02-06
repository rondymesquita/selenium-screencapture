package br.com.example.screenshot;

import java.awt.Color;

import br.com.example.image.ImageDiffConfig;
import br.com.example.util.Logger;

public class ScreenshooterCapabilites {
	
	private String inputFolder;
	private String outputFolder;
	private ImageDiffConfig imageDiffConfig;
	
	public ScreenshooterCapabilites(){
		inputFolder = "./screenshot/input/";
		outputFolder = "./screenshot/output/";
		imageDiffConfig = new ImageDiffConfig();
	}

	public Color getHighlightColor() {
		return imageDiffConfig.highlightColor;
	}

	public void setHighlightColor(Color highlightColor) {
		imageDiffConfig.highlightColor = highlightColor;
	}

	public boolean isShouldGenerateDiffImage() {
		return imageDiffConfig.shouldGenerateDiffImage;
	}

	public void setShouldGenerateDiffImage(boolean shouldGenerateDiffImage) {
		imageDiffConfig.shouldGenerateDiffImage = shouldGenerateDiffImage;
	}

	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public ImageDiffConfig getImageDiffConfig() {
		return imageDiffConfig;
	}
	
	public static class ScreenshooterCapabilitesValidator{
		
		private static StringBuilder errors = new StringBuilder();

		private static boolean isValid(ScreenshooterCapabilites capabilites) {
			boolean isValid = true;
			if(capabilites.getInputFolder().equals(capabilites.getOutputFolder())){
				errors.append("ScreenshooterCapabilites: Input folder must be different of output folder.");
				isValid = false;
			}
			return isValid;
		}
		
		public static void validate(ScreenshooterCapabilites capabilites) throws Exception {
			if(!isValid(capabilites)){
				Logger.logSevere(errors.toString());
				throw new Exception(errors.toString());
			}
		}
		
		public static String getErrors() {
			return errors.toString();
		}
	}
}
