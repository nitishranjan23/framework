package steps;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import pageobjects.GoogleMap;
import pageobjects.NavigationPages;
import utils.ActionMethods;

public class PageNavigation {

	private static final Logger LOG = Logger.getLogger(PageNavigation.class);
	WebDriver driver = SetUp.driver;
	ActionMethods action = new ActionMethods();
	GoogleMap googleMap = SetUp.googleMapPage;
	NavigationPages navigationPage = SetUp.navigationPage;
	Scenario scenario = SetUp.scenario;
	

	@Then("^verify Sign in page is opened$")
	public void verify_Sign_in_page_is_opened() throws Throwable {
		try {
			action.sync(driver, navigationPage.signInContainer);
			LOG.info("Sign in page is opened successfully");
		} catch (Exception e) {
			LOG.error("Sign in page didn't open");
			scenario.write("Sign in page didn't open");
			throw e;
		}
	}

	@Then("^verify \"([^\"]*)\" page is opened$")
	public void verify_page_is_opened(String pageName) throws Throwable {
		try {
			action.sync(driver, navigationPage.getPageWithHeading(driver, pageName));
			LOG.info(pageName +" page is opened successfully");
		} catch (Exception e) {
			LOG.error(pageName +" page didn't open");
			scenario.write(pageName +" page didn't open");
			throw e;
		}
	}

	@Then("^verify mapsynq mobile information page is opened in new browser tab$")
	public void verify_mapsynq_mobile_information_page_is_opened_in_new_browser_tab() throws Throwable {
		try {
			for(String handle: driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			action.waitForPageLoad(driver);
			Assert.assertTrue("Mapsynq mobile information page didn't open in new browser tab",driver.getTitle().trim().equalsIgnoreCase("mapSYNQ Mobile") && driver.getWindowHandles().size()>1);
			LOG.info("Mapsynq mobile information page opened successfully in new browser tab");
		} catch (AssertionError ae) {
			LOG.error("Mapsynq mobile information page didn't open in new browser tab");
			throw new Throwable("Mapsynq mobile information page didn't open in new browser tab");
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}
	}

	@Then("^verify Galactio page is opened in new browser tab$")
	public void verify_Galactio_page_is_opened_in_new_browser_tab() throws Throwable {
		try {
			for(String handle: driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			action.waitForPageLoad(driver);
			Assert.assertTrue("Galactio page didn't open in new browser tab",driver.getTitle().trim().equalsIgnoreCase("Galactio") && driver.getWindowHandles().size()>1);
			LOG.info("Galactio page opened successfully in new browser tab");
		} catch (AssertionError ae) {
			LOG.error("Galactio page didn't open in new browser tab");
			throw new Throwable("Galactio page didn't open in new browser tab");
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}
	}

	@Then("^verify Google Play page is opened in new browser tab$")
	public void verify_Google_Play_page_is_opened_in_new_browser_tab() throws Throwable {
		try {
			for(String handle: driver.getWindowHandles()) {
				driver.switchTo().window(handle);
			}
			action.waitForPageLoad(driver);
			Assert.assertTrue("Google Play page didn't open in new browser tab",driver.getWindowHandles().size()>1);
			action.sync(driver, navigationPage.googlePlayLogo);
			LOG.info("Google Play page is opened in new browser tab");
		} catch (AssertionError ae) {
			LOG.error("Google Play page didn't open in new browser tab");
			throw new Throwable("Google Play page didn't open in new browser tab");
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}
	}

	@Then("^verify \"([^\"]*)\" popup is displayed$")
	public void verify_popup_is_displayed(String title) throws Throwable {
	     try {
	    	 action.sync(driver, googleMap.getPopupByTitle(driver, title));
	    	 LOG.info(title + " popup is diaplayed");
	     } catch (Exception e) {
			LOG.error(title + " popup is not displayed");
			scenario.write(title + " popup is not displayed");
			LOG.error(e);
			throw e;
		}
	}
}
