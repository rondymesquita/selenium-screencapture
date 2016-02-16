
# Selenium Screenshots Library

## Proposal
The library takes screenshots from current page and compare with screenshots previous saved. It is useful to verify whether layout, color and element positions are correct.
The library compare the two screenshots and return whether they are ok or not.

## Usage


### ExampleOne
```java
//Create the rule
@Rule
public TestName testNameRule = new TestName();
private WebDriver driver;
private Screenshooter screenshooter;

@Before
public void before() throws Exception {
	driver = new FirefoxDriver();
	driver.manage().window().maximize();

	//Create the screenshoter object passing the driver and the rule
	screenshooter = new Screenshooter(driver, testNameRule);
}

@After
public void after() {
	driver.quit();
}

@Test
public void exampleOne() throws Exception{

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
```

### ExampleTwo
You can create custom capabilities to configure some options:
```java
ScreenshooterCapabilites screenshotCapabilites = new ScreenshooterCapabilites();
screenshotCapabilites.setInputFolder("./custom-folder/input");
screenshotCapabilites.setOutputFolder("./custom-folder/output");
screenshotCapabilites.setHighlightColor(HighlightColor.CYAN);

//Optional to not generate the diff and just compare the images
screenshotCapabilites.setShouldGenerateDiffImage(false);

//Create the screenshoter object passing the driver and the rule
screenshooter = new Screenshooter(driver, testNameRule, screenshotCapabilites);
```

You can see the full example on classes ExampleOne and ExampleTwo.

### How it works
When you create your test and run for the very first time, the lib will take the screenshots and save in output folder. At this moment, the test will fail. So now you have the screenshots of current state of screen. After that, you can copy the test folder with screenshots from output folder into input folder and run the test again. The test should pass because the lib will compare the screenshots and verify whether they are equals. If your screen change, the comparison will fail and the lib will generate two files in output folder: ** *-diff.png ** file showing what are the differences and * ** -expected.png ** file which is the expected screenshot copied from input folder. To fix that, you have two options: change your screen to match with expected or copy the output file (with same name of input file, with -diff or -expected suffix), to input folder.

### Diff example

You can see diff examples in **./test-assets**


## Requirements
- Maven3+
- **JAVA_HOME** on path (tested with java8)

## Configuring
### Dependencies
```bash
mvn eclipse:eclipse
```

### Running
```bash
mvn test
```

### Possible and Known issues (Working on it)
- Should take screenshots only when css3 transitions are ended
- Depending on screen size, test may have different behaviors

### Working on features
- Rate of differences: Define whether pixels are different based on their color.
