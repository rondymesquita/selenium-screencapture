package br.com.example;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import br.com.example.image.HighlightColor;
import br.com.example.image.ImageDiff;
import br.com.example.image.ImageDiffConfig;

public class ImageDiffTest {
	
	@Rule
	public TestName testNameRule = new TestName();
	
	private String testName;
	private String folderName;
	
	private ImageDiff imageDiff;
	
	@Before
	public void before() {
		imageDiff = new ImageDiff();
		testName = testNameRule.getMethodName() + "/";
		folderName 	= "./test-assets/" + testName;
	}

	@After
	public void after() {
		
	}
	
	@Test
	public void shouldBeEquals() throws Exception{
		
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
	}
	
	@Test
	public void shouldBeDifferentAndGenerateDiff() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		assertTrue(diff.delete());
	}
	
	@Test
	public void shouldBeDifferentAndNotGenerateDiff() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.shouldGenerateDiffImage = false;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(!diff.exists());
	}
	
	@Test
	public void shouldGenerateDiffWithMagentaColorWithoutConfig() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
		
	}
	
	@Test
	public void shouldGenerateDiffWithMagentaColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.MAGENTA;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}
	
	@Test
	public void shouldGenerateDiffWithYellowColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.YELLOW;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}
	
	@Test
	public void shouldGenerateDiffWithCyanColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.CYAN;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}
	
	@Test
	public void shouldGenerateDiffWithGreenColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.GREEN;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}
	
	@Test
	public void shouldGenerateDiffWithRedColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.RED;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}
	
	@Test
	public void shouldGenerateDiffWithBlueColor() throws Exception{
		File actual 	= new File(folderName, "1.png");
		File expected 	= new File(folderName, "1-expected.png");
		
		ImageDiffConfig config = new ImageDiffConfig();
		config.highlightColor = HighlightColor.BLUE;
		
		imageDiff = new ImageDiff(config);
		imageDiff.setImages(actual, expected);
		assertTrue(!imageDiff.equals());
		
		File diff = new File(folderName,"1-diff.png");
		assertTrue(diff.exists());
		
		/*
		 * Compare the generated diff with saved one
		 */
		actual 		= new File(folderName, "1-diff.png"); //this one will be generated on test
		expected 	= new File(folderName, "1-diff-expected.png");
		
		imageDiff = new ImageDiff();
		imageDiff.setImages(actual, expected);
		assertTrue(imageDiff.equals());
		
		assertTrue(actual.delete());
	}

}
