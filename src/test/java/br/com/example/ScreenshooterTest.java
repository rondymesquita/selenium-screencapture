package br.com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.example.image.HighlightColor;
import br.com.example.image.ImageDiff;
import br.com.example.page.DuckDuckGoPage;
import br.com.example.screenshot.Screenshooter;
import br.com.example.screenshot.ScreenshooterCapabilites;

public class ScreenshooterTest {
	
	@Rule
	public TestName testNameRule = new TestName();
	private WebDriver driver;
	private Screenshooter screenshooter;
	private ScreenshooterCapabilites screenshotCapabilites;
	private DuckDuckGoPage duckDuckGoPage;
	private String inputFolder;
	private String outputFolder;
	private String testName;
	private String fileName;
	private ImageDiff imageDiff;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void before() throws Exception {
		testName = testNameRule.getMethodName();
		
		inputFolder = "./test-assets/screenshot/input/";
		outputFolder = "./test-assets/screenshot/output/";
		
		fileName = testName + "/" + testName;
		
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		duckDuckGoPage = new DuckDuckGoPage(driver);

		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.MAGENTA);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);

		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);

	}

	@After
	public void after() {
		driver.quit();
	}

	@Test
	public void shouldTakeOneScreenshotAndInputFileShouldNotExists() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		String msg = String.format("The expected file %s%s was not found", screenshotCapabilites.getInputFolder(), testName + "/" + testName + "-1");
		List<String> errors = screenshooter.getErrors();
		assertTrue(errors.contains(msg));
	}
	
	@Test
	public void shouldTakeTwoScreenshotAndInputFileShouldNotExists() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		duckDuckGoPage.setAnyTextOnSearchField();
		screenshooter.takeScreenshot();
		
		assertTrue(!screenshooter.isOk());
		String msg = String.format("The expected file %s%s was not found", screenshotCapabilites.getInputFolder(), testName + "/" + testName + "-1");
		String msg2 = String.format("The expected file %s%s was not found", screenshotCapabilites.getInputFolder(), testName + "/" + testName + "-2");
		List<String> errors = screenshooter.getErrors();
		assertTrue(errors.contains(msg));
		assertTrue(errors.contains(msg2));
	}
	
	@Test
	public void shouldTakeOneScreenshotImageDoNotMatchAndVerifyMessages() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		String msg = String.format("The image %s%s does not match with expected", screenshotCapabilites.getOutputFolder(), testName + "/" + testName + "-1");
		List<String> errors = screenshooter.getErrors();
		assertTrue(errors.contains(msg));
	}
	
	@Test
	public void shouldTakeTwoScreenshotImageDoNotMatchAndVerifyMessages() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		duckDuckGoPage.setAnyTextOnSearchField();
		screenshooter.takeScreenshot();
		
		assertTrue(!screenshooter.isOk());
		String msg1 = String.format("The image %s%s does not match with expected", screenshotCapabilites.getOutputFolder(), testName + "/" + testName + "-1");
		String msg2 = String.format("The image %s%s does not match with expected", screenshotCapabilites.getOutputFolder(), testName + "/" + testName + "-2");
		List<String> errors = screenshooter.getErrors();
		assertTrue(errors.contains(msg1));
		assertTrue(errors.contains(msg2));
	}
	
	@Test
	public void shouldTakeOneScreenshotAndInputFileShouldExists() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(screenshooter.isOk());
		
		File inputFile = new File(inputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		File expectedFile = new File(outputFolder, fileName + "-1-expected.png");
		assertTrue(!expectedFile.exists());
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(!diffFile.exists());
	}
	
	@Test
	public void shouldTakeTwoScreenshotAndInputFileShouldExists() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		duckDuckGoPage.setAnyTextOnSearchField();
		screenshooter.takeScreenshot();
		
		assertTrue(screenshooter.isOk());
		
		File inputFile = new File(inputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		File expectedFile = new File(outputFolder, fileName + "-1-expected.png");
		assertTrue(!expectedFile.exists());
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(!diffFile.exists());
		
		inputFile = new File(inputFolder, fileName + "-2.png");
		assertTrue(inputFile.exists());
		outputFile = new File(outputFolder, fileName + "-2.png");
		assertTrue(outputFile.exists());
		expectedFile = new File(outputFolder, fileName + "-2-expected.png");
		assertTrue(!expectedFile.exists());
		diffFile = new File(outputFolder, fileName + "-2-diff.png");
		assertTrue(!diffFile.exists());
		
	}
	
	@Test
	public void shouldTakeOneScreenshotAndGenerateDiff() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		assertTrue(!screenshooter.isOk());
		
		File inputFile = new File(inputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		File expectedFile = new File(outputFolder, fileName + "-1-expected.png");
		assertTrue(expectedFile.exists());
		assertTrue(expectedFile.delete());
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		assertTrue(diffFile.delete());
	}
	
	@Test
	public void shouldTakeTwoScreenshotsAndGenerateDiff() throws Exception {
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		duckDuckGoPage.setAnyTextOnSearchField();
		screenshooter.takeScreenshot();
		
		assertTrue(!screenshooter.isOk());
		
		File inputFile = new File(inputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		File expectedFile = new File(outputFolder, fileName + "-1-expected.png");
		assertTrue(expectedFile.exists());
		assertTrue(expectedFile.delete());
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		assertTrue(diffFile.delete());
		
		inputFile = new File(inputFolder, fileName + "-2.png");
		assertTrue(inputFile.exists());
		outputFile = new File(outputFolder, fileName + "-2.png");
		assertTrue(outputFile.exists());
		expectedFile = new File(outputFolder, fileName + "-2-expected.png");
		assertTrue(expectedFile.exists());
		assertTrue(expectedFile.delete());
		diffFile = new File(outputFolder, fileName + "-2-diff.png");
		assertTrue(diffFile.exists());
		assertTrue(diffFile.delete());
	}

	@Test
	public void shouldBuildDefaultCapabilitiesWithSuccess() throws Exception{
		screenshooter = new Screenshooter(driver, testNameRule);
		ScreenshooterCapabilites capabilities = screenshooter.getCapabilites();
		assertEquals("./screenshot/input/", capabilities.getInputFolder());
		assertEquals("./screenshot/output/", capabilities.getOutputFolder());
		assertEquals(HighlightColor.MAGENTA, capabilities.getHighlightColor());
		assertEquals(true, capabilities.isShouldGenerateDiffImage());
	}
	
	@Test
	public void shouldBuildCustomCapabilitiesWithSuccess() throws Exception{
		
		inputFolder = "./test-assets/screenshot/another-input/";
		outputFolder = "./test-assets/screenshot/another-output/";
		
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.CYAN);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshotCapabilites.setShouldGenerateDiffImage(false);
		
		screenshooter = new Screenshooter(driver, testNameRule,screenshotCapabilites);
		ScreenshooterCapabilites capabilities = screenshooter.getCapabilites();
		assertEquals(inputFolder, capabilities.getInputFolder());
		assertEquals(outputFolder, capabilities.getOutputFolder());
		assertEquals(HighlightColor.CYAN, capabilities.getHighlightColor());
		assertEquals(false, capabilities.isShouldGenerateDiffImage());
		
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		
		File inputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		
		assertTrue(screenshooter.isOk());
	}
	
	@Test
	public void shouldBuildCustomCapabilitiesWithSuccessAndGenerateDiff() throws Exception{
		
		inputFolder = "./test-assets/screenshot/another-input/";
		outputFolder = "./test-assets/screenshot/another-output/";
		
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.CYAN);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshotCapabilites.setShouldGenerateDiffImage(true);
		
		screenshooter = new Screenshooter(driver, testNameRule,screenshotCapabilites);
		ScreenshooterCapabilites capabilities = screenshooter.getCapabilites();
		assertEquals(inputFolder, capabilities.getInputFolder());
		assertEquals(outputFolder, capabilities.getOutputFolder());
		assertEquals(HighlightColor.CYAN, capabilities.getHighlightColor());
		assertEquals(true, capabilities.isShouldGenerateDiffImage());
		
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		
		File inputFile = new File(inputFolder, fileName + "-1.png");
		assertTrue(inputFile.exists());
		
		File outputFile = new File(outputFolder, fileName + "-1.png");
		assertTrue(outputFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-expected.png");
		assertTrue(expectedFile.exists());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		assertTrue(!screenshooter.isOk());
	}
	
	@Test
	public void shouldThrowExceptionWhenInputAndOutputAreEqual() throws Exception{
		expectedException.expect(Exception.class);
		expectedException.expectMessage("ScreenshooterCapabilites: Input folder must be different of output folder.");
		
		inputFolder = "./folder";
		outputFolder = "./folder";
		
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
	}
	
	@Test
	public void shouldGenerateDiffWithMagentaColorWithoutConfig() throws Exception{
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithMagentaColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.MAGENTA);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithCyanColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.CYAN);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithYellowColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.YELLOW);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithBlueColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.BLUE);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithGreenColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.GREEN);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldGenerateDiffWithRedColor() throws Exception{
		screenshotCapabilites = new ScreenshooterCapabilites();
		screenshotCapabilites.setHighlightColor(HighlightColor.RED);
		screenshotCapabilites.setInputFolder(inputFolder);
		screenshotCapabilites.setOutputFolder(outputFolder);
		screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
		duckDuckGoPage.open();
		screenshooter.takeScreenshot();
		assertTrue(!screenshooter.isOk());
		
		File diffFile = new File(outputFolder, fileName + "-1-diff.png");
		assertTrue(diffFile.exists());
		
		File expectedFile = new File(outputFolder, fileName + "-1-diff-expected.png");
		assertTrue(diffFile.exists());
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(diffFile, expectedFile);
		assertTrue(imageDiff.equals());
	}
	
}
