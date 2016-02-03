package br.com.example.screenshot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import br.com.example.image.ImageDiff;
import br.com.example.image.ImageDiffConfig;
import br.com.example.screenshot.ScreenshooterCapabilites.ScreenshooterCapabilitesValidator;
import br.com.example.util.Logger;

public class Screenshooter{

	private int shootCount = 1;
	private String testName;
	private String folderName;
	private WebDriver driver;
	private boolean areScreenshotsOk = true;
	private ImageDiff imageDiff;
	private List<String> errorList = new ArrayList<String>();
	private ScreenshooterCapabilites capabilites;
	
	public Screenshooter(WebDriver driver, TestName testNameRule, ScreenshooterCapabilites capabilites) throws Exception {
		this(driver, testNameRule);
		
		if(capabilites != null){
			ScreenshooterCapabilitesValidator.validate(capabilites);
			this.capabilites = capabilites;
		}
	}

	public Screenshooter(WebDriver driver, TestName testNameRule) {
		this.driver = driver;

		this.testName = testNameRule.getMethodName();
		this.folderName = testNameRule.getMethodName();
		this.shootCount = 1;
		
		if(capabilites == null){
			capabilites = new ScreenshooterCapabilites();
		}
		
	}

	/**
	 * Takes screenshot from current opened web page and compare with
	 * expected saved image on input folder
	 * @throws IOException 
	 */
	public void takeScreenshot() throws IOException {

		String screenshotName = takeScreenshotFromPage();
		
		if (!areImagesEqual(screenshotName)) {
			areScreenshotsOk = false;
		}
	}
	
	/**
	 * Takes screenshot from current opened web page
	 * @return
	 */
	private String takeScreenshotFromPage() {
		try {
			
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File file = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

			String screenshotName = String.format("%s/%s-%s", folderName, testName, shootCount++);
			Logger.logInfo(screenshotName);

			FileUtils.copyFile(file, new File(capabilites.getOutputFolder(), screenshotName + ".png"));
			
			return screenshotName;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Verify if images are equals and generate a diff.
	 * 
	 * @param screenshotName
	 * @return
	 * @throws IOException
	 */
	private boolean areImagesEqual(String screenshotName) throws IOException {

		File actual = new File(capabilites.getOutputFolder(), screenshotName + ".png");
		File expected = new File(capabilites.getInputFolder(), screenshotName + ".png");

		if (!expected.exists()) {
			String msg = String.format("The expected file %s%s was not found", capabilites.getInputFolder(), screenshotName);
			Logger.logSevere(msg);
			errorList.add(msg);
			return false;
		}

		imageDiff = new ImageDiff(capabilites.getImageDiffConfig());
		imageDiff.setImages(actual, expected);
		boolean areImagesEqual = imageDiff.equals();

		if (!areImagesEqual) {
			FileUtils.copyFile(expected, new File(capabilites.getOutputFolder(), screenshotName + "-expected.png"));
			String msg = String.format("The image %s%s does not match with expected", capabilites.getOutputFolder(), screenshotName);
			Logger.logSevere(msg);
			errorList.add(msg);
		}

		return areImagesEqual;

	}
	
	public boolean isOk() {
		//assertTrue(getErrorsFromErrorList(), areScreenshotsOk);
		if(!areScreenshotsOk){
			Logger.logSevere(getErrors());
		}
		
		return areScreenshotsOk;
	}
	
	public String getErrors() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : errorList) {
			stringBuilder.append(string);
		}
		return stringBuilder.toString();
	}

	public ScreenshooterCapabilites getCapabilites() {
		return capabilites;
	}

}
