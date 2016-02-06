package br.com.example.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.example.util.Logger;

public class ImageDiff {
	
	private BufferedImage bufferedImage;
	private BufferedImage diffLayer;
	private BufferedImage actual;
	private BufferedImage expected;

	private File actualFile;

	private Graphics2D outputGraphics2d;
	
	private int maxWidth;
	private int maxHeight;

	private boolean areImagesEqual = true;
	private ImageDiffConfig config;

	public ImageDiff() {
		this.config = new ImageDiffConfig();
	}

	public ImageDiff(ImageDiffConfig config) {
		this.config = config;
	}

	public void setImages(File actualFile, File expectedFile) {
		try {
			this.actualFile = actualFile;

			actual = ImageIO.read(actualFile);
			expected = ImageIO.read(expectedFile);

			maxWidth = getMaxWidth();
			maxHeight = getMaxHeight();

			bufferedImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
			outputGraphics2d = bufferedImage.createGraphics();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean equals() throws IOException {
		generateDiff();
		return areImagesEqual;
	}
	
	/**
	 * Generate the diff of two images If they are different, so the diff image is generated and saved
	 * @throws IOException
	 */
	private void generateDiff() throws IOException {
		
		diffLayer = generateDiffLayer();
		if (!areImagesEqual) {

			outputGraphics2d.drawImage(actual, 0, 0, null);

			outputGraphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (0.5)));
			outputGraphics2d.drawImage(expected, 0, 0, null);

			outputGraphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (0.2)));
			outputGraphics2d.drawImage(diffLayer, 0, 0, null);

			if (config.shouldGenerateDiffImage) {
				saveImageToFile();
			}
			
			closeResources();
		}
	}

	/**
	 * Compare two images and generate the diff
	 * @return the layer with diff painted on it
	 */
	private BufferedImage generateDiffLayer() {

		BufferedImage diffLayer = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);

		compare: for (int x = 0; x < maxWidth; x++) {
			for (int y = 0; y < maxHeight; y++) {

				try {
					if (actual.getRGB(x, y) != expected.getRGB(x, y)) {
						areImagesEqual = false;
						diffLayer.setRGB(x, y, config.highlightColor.getRGB());
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					areImagesEqual = false;
					diffLayer.setRGB(x, y, config.highlightColor.getRGB());
//					break compare;
				}
			}
		}

		return diffLayer;
	}

	private int getMaxWidth() {
		return Math.max(this.actual.getWidth(), this.expected.getWidth());
	}

	private int getMaxHeight() {
		return Math.max(this.actual.getHeight(), this.expected.getHeight());
	}

	/**
	 * Save on disc the merged images with diff
	 * @throws IOException
	 */
	private void saveImageToFile() throws IOException {
		String ext = actualFile.getName().substring(actualFile.getName().length() - 3, actualFile.getName().length());
		String output = actualFile.getParentFile().toString();
		String name = actualFile.getName().replace("." + ext, "");

		String filename = String.format("%s%s%s", name, "-diff.", ext);
		ImageIO.write(bufferedImage, ext.toUpperCase(), new File(output, filename));
	}
	
	/**
	 * Close all resources (BufferedImages) used
	 */
	private void closeResources(){
		bufferedImage.flush();
		diffLayer.flush();
		actual.flush();
		expected.flush();
	}

}
