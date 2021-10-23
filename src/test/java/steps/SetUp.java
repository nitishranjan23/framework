package steps;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.GoogleMap;
import pageobjects.NYSEPageObject;
import pageobjects.NavigationPages;
import utils.PropertiesFileReader;

/**
 * Setup class for performing all setups like launching the browser before
 * starting test and closing the browser after test.
 * 
 * @author Nitish
 */
public class SetUp {

	static {
		DOMConfigurator.configure("log4j.xml"); // Configure logger from log4j.xml file
	}
	
	public static WebDriver driver;
	public static GoogleMap googleMapPage;
	public static Properties properties;
	public static NavigationPages navigationPage;
	static PropertiesFileReader fileReader = new PropertiesFileReader();
	public static Actions action;
	public static Scenario scenario;
	static final Logger LOG = Logger.getLogger(SetUp.class);
	private String browser = null;
	public static NYSEPageObject nysePage;
	
	/**
	 * Initialization method: Here browser is selected based on data in
	 * globalConfig.properties file. Then {@link DesiredCapabilities} of that
	 * browser is provided and finally the browser is launched with the provided
	 * capabilities.
	 * 
	 * @author Nitish
	 */

	@Before
	@SuppressWarnings("deprecation")
	public void setupTest(Scenario scenario) {
		SetUp.scenario = scenario;
		System.out.println("Scenario Name: " + scenario.getName());
		properties = fileReader.loadPropertiesFile("globalConfig.properties"); // reading properties file

		browser = properties.getProperty("browser").toLowerCase();
		String headless = properties.getProperty("headless").toLowerCase();
		// For headless execution I will be using Chrome here in headless mode
		if (headless.equalsIgnoreCase("true")) {
			browser = "Headless";
			LOG.info("Performing Headless Browser Testing");
			ChromeOptions chromeOptions = new ChromeOptions();
			// Getting current screen resolution
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = screenSize.width;
			int height = screenSize.height;
			System.out.println(width + "x" + height);
			// For launching headless chrome in same size as of current screen resolution
			// chromeOptions.addArguments("window-size="+width+"x"+height);
			chromeOptions.addArguments("window-size=1366x768");
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--headless");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
		} else {
			switch (browser) {
			case "ie":
				LOG.info("Testing Browser: Internet Explorer");
				DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver(dc);
				break;

			case "chrome":
				LOG.info("Testing Browser: Chrome");
				WebDriverManager.chromedriver().setup();
				//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//exe//chromedriver.exe");
				driver = new ChromeDriver();
				break;

			case "opera":
				LOG.info("Testing Browser: Opera");
				WebDriverManager.operadriver().setup();
				DesiredCapabilities capabilities = new DesiredCapabilities();
				OperaOptions options = new OperaOptions();
				/**
				 * Due to some technical limitations with Opera, It is required to provide path
				 * of opera executables.
				 * System.getProperty("user.home") gives path up to C:/users/<user name>
				 */
				options.setBinary(System.getProperty("user.home")
						+ "\\AppData\\Local\\Programs\\Opera\\58.0.3135.65_0\\opera.exe");
				capabilities.setCapability(OperaOptions.CAPABILITY, options);
				driver = new OperaDriver(capabilities);
				break;

			case "firefox":
				LOG.info("Testing Browser: Firefox");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;

			default:
				LOG.error("Unsupported browser. Please select browser from: Chrome, IE, Firefox, Opera");
			}
		}
		driver.manage().window().maximize(); // Maximizing the browser window
		googleMapPage = PageFactory.initElements(driver, GoogleMap.class);
		navigationPage = PageFactory.initElements(driver, NavigationPages.class);
		nysePage = PageFactory.initElements(driver, NYSEPageObject.class);
		
		action = new Actions(driver);
		//driver.get(properties.getProperty("URL"));
	}
	
	//In case of multiple @Before and @After, execution is happening on the order it is written.
	//To change the order of execution, order is provided.
	//Default order is 10000
	//For @Before, less order will execute first
	//For @After, larger order will execute first
	@Before(order = 1)
	public void asetup2() {
		System.out.println("befor 2");
	}

	@After
	public void tear() {
		System.out.println("After 2");
	}
	/**
	 * To close all driver instances and take screenshot of failed Testcase and
	 * attach it to report file also it will save taken failed screenshots to
	 * screenshot/failed folder
	 * 
	 * @author Nitish
	 * @throws IOException
	 */
	@After
	public void closeResources(Scenario scenario) throws IOException {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		if (scenario.isFailed()) {
			// Taking screenshot and adding/embedding it to report
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");

			// Saving screenshot of failed scenarios in screenshot/failed folder
			File folderLocation = new File(
					"screenshots/failed/" + System.currentTimeMillis() + "_" + scenario.getName() +"_"+ browser + ".png");
			FileUtils.copyFile(screenshotFile, folderLocation);
		} else {
			// Saving screenshot of passed scenarios in screenshot/passed folder
			File folderLocation = new File(
					"screenshots/passed/" + System.currentTimeMillis() + "_" + scenario.getName() +"_"+ browser + ".png");
			FileUtils.copyFile(screenshotFile, folderLocation);
		}

		driver.close();
		driver.quit();

	}
	
	@After(order = 100000)
	public void tear2() {
		System.out.println("After 3");
	}
}
