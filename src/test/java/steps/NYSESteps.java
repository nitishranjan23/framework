package steps;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageobjects.GoogleMap;
import pageobjects.NYSEPageObject;
import utils.ActionMethods;

public class NYSESteps {

	private static final Logger LOG = Logger.getLogger(NYSESteps.class);
	WebDriver driver = SetUp.driver;
	Properties prop = SetUp.properties;
	ActionMethods action = new ActionMethods();
	Actions actions = SetUp.action;
	NYSEPageObject nysePage = SetUp.nysePage;
	Scenario scenario = SetUp.scenario;

	@Given("^NYSE page is loaded$")
	public void nyse_page_is_loaded() throws Throwable {
		try {
			driver.get(prop.getProperty("NYSE_URL"));
			action.waitForPageLoad(driver);
		} catch (Exception e) {
			LOG.error("NYSE page didn't loaded");
			throw e;
		}

	}

	@Then("^search for \"([^\"]*)\"$")
	public void search_for(String arg1) throws Throwable {
		try {
			action.sync(driver, nysePage.searchTB);
			nysePage.searchTB.sendKeys(arg1);
			action.waitForPageLoad(driver);
			action.sync(driver, nysePage.firstSearchResult);
			nysePage.firstSearchResult.click();
			action.waitForPageLoad(driver);
			LOG.info("Navigated to searched page");
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}

	}

	Map<String, String> priceData = new HashMap<String, String>();

	@Then("^capture share prices between \"([^\"]*)\" and \"([^\"]*)\"$")
	public void capture_share_prices_between_and(String startDate, String endDate) throws Throwable {
		try {
			action.sync(driver, nysePage.priceChart);
			action.sync(driver, nysePage.priceDetailsTable);
			action.scrollIntoView(driver, nysePage.priceChart);
			actions.moveToElement(nysePage.priceChart).click().build().perform();
			action.sync(driver, nysePage.getChartValueAsPerLabel(driver, "Date"));

			int chartWidth = nysePage.priceChart.getSize().getWidth();
			int chartX = nysePage.priceChart.getSize().getWidth();
			actions.moveToElement(nysePage.priceChart).moveByOffset(-chartX / 2, 0).build().perform();
			System.out.println("Date: " + nysePage.getChartValueAsPerLabel(driver, "Date").getText());

			int offset = chartX / 250;

			System.out.println("Length: " + chartX);
			while (true) {
				try {
					actions.moveByOffset(offset, 0).build().perform();

				} catch (MoveTargetOutOfBoundsException me) {
					LOG.info("Reached Out of Range");
					break;
				}
				String dataVal = nysePage.getChartValueAsPerLabel(driver, "Date").getText();
				Date currDate = new SimpleDateFormat("MM/dd/yy").parse(dataVal);
				String price = nysePage.getChartValueAsPerLabel(driver, "Close").getText();

				System.out.println("Date: " + dataVal);
				System.out.println("Close: " + price);

				Date start = new SimpleDateFormat("MM/dd/yy").parse(startDate);
				Date end = new SimpleDateFormat("MM/dd/yy").parse(endDate);

				// if(start.after(currDate) && end.before(currDate) ) {
				if (currDate.compareTo(start) > 0 && currDate.compareTo(end) < 0) {
					System.out.println("======inside");
					priceData.put(dataVal, price);
				}

				if (currDate.after(end)) {
					LOG.info("Reached :" + end);
					break;
				}

			}

		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}

	}

	@Then("^post the JSON file$")
	public void post_the_JSON_file() throws Throwable {
		System.out.println(priceData);
		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(new File(System.getProperty("user.dir") + "\\target\\priceData.json"), priceData);

		/*
		 * URL url = new URL
		 * ("https://testathon-service.herokuapp.com/api/v2/stocks/data");
		 * 
		 * HttpURLConnection con = (HttpURLConnection)url.openConnection();
		 * con.setRequestMethod("POST"); con.setRequestProperty("Content-Type",
		 * "application/json; utf-8"); con.setRequestProperty("Accept",
		 * "application/json"); con.setDoOutput(true);
		 * 
		 * 
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * 
		 * HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<String>
		 * response = restTemplate.put(url, entity);
		 */
	}

}
