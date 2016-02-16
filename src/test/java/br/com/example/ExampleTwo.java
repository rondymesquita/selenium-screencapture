package br.com.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.example.image.HighlightColor;
import br.com.example.screenshot.Screenshooter;
import br.com.example.screenshot.ScreenshooterCapabilites;

public class ExampleTwo {
	
	
	//Create the rule
	@Rule public TestName testNameRule = new TestName();
	private WebDriver driver;
	private Screenshooter screenshooter;
	
	@Before
	public void before() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
		ScreenshooterCapabilites screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setInputFolder("./custom-folder/input");
		screenshotCapabilites.setOutputFolder("./custom-folder/output");
		screenshotCapabilites.setHighlightColor(HighlightColor.CYAN);
		
		//Optional to not generate the diff and just compare the images
		screenshotCapabilites.setShouldGenerateDiffImage(false);
		
		//Create the screenshoter object passing the driver and the rule
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
	}
	
	@After
	public void after() {
		driver.quit();
	}
	
	@Test
	public void exampleTwo() throws Exception{
		
		/*
		 * Do any action
		 */
		driver.get("https://duckduckgo.com");
		
		/*
		 * Take a screenshot, save to ./screenshot/output/exampleOne/exampleOne-1.png
		 * and compare with ./screenshot/input/exampleOne/exampleOne-1.png
		 */
		screenshooter.takeScreenshot();
		
		/*
		 * Do any action
		 */
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("Darth Vader");
		
		/*
		 * Take a screenshot, save to ./screenshot/output/exampleOne/exampleOne-2.png
		 * and compare with ./screenshot/input/exampleOne/exampleOne-2.png
		 */
		screenshooter.takeScreenshot();
		
		/*
		 * Verify whether all screenshots are ok
		 */
		Assert.assertTrue(screenshooter.isOk());
		
	}
	
}
