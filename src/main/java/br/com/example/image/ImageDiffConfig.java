package br.com.example.image;

import java.awt.Color;

public class ImageDiffConfig {

	public Color highlightColor;
	public boolean shouldGenerateDiffImage;
	
	public ImageDiffConfig(){
		highlightColor = HighlightColor.MAGENTA;
		shouldGenerateDiffImage = true;
	}
	
	
}
