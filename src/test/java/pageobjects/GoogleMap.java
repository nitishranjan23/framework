package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import steps.SetUp;

/**
 * This is page object class for background Google Map
 * Here we are keeping objects which appears over the google map like any popup information
 * or any icon on map
 * 
 * Most {@link WebElement}s in this class is find by using {@link FindBy} 
 * which uses {@link PageFactory} which is defined in {@link SetUp} class using
 * PageFactory.initElements
 * 
 * @author Nitish
 * */
public class GoogleMap {

	/**
	 * This method will get top panel buttons like 
	 * Traffic, Incidents, Parking, Cameras and Tolls  by name.
	 * 
	 * @author Nitish
	 * @param driver {@link WebDriver} instance
	 * @param buttonName Name of top panel button
	 * @return It will return particular tab as {@link WebElement}
	 * 
	 * */
	public WebElement getTopPannelButtons(WebDriver driver, String buttonName) {
		return driver.findElement(By.cssSelector("div.buttonPanel div[title *= '"+buttonName+"']"));
	}
	
	//Since these are svg element, it is more easy to work with CSS
	@FindBy(how =How.CSS, using = "g[style *='visible'] image")
	public List<WebElement> incidentsOnMap;
	
	@FindBy(how =How.CSS, using = "div.speedLegendPanel")
	public WebElement liveTrafficLegend;
	
	/**
	 * This method will get the left, right, up, down navigation button.
	 * 
	 * @author Nitish
	 * @param driver {@link WebDriver} instance
	 * @param buttonName Name of button
	 * @return particular buttons in form of {@link WebElement} 
	 * 
	 * */
	public WebElement getLeftRightUpDownButtonOnMap(WebDriver driver, String buttonName) {
		return driver.findElement(By.cssSelector("div[class *= 'ZoomBar'] div[id $= '"+buttonName+"']"));
	}
	
	/**
	 * This method will get the zoomin zoomout button on the zoom map slider.
	 * 
	 * @author Nitish
	 * @param driver {@link WebDriver} instance
	 * @param buttonName Name of button
	 * @return particular buttons in form of {@link WebElement} 
	 * 
	 * */
	public WebElement getZoomInZoomOutButtonOnZoomSliderOnMap(WebDriver driver, String buttonName) {
		return driver.findElement(By.cssSelector("div[id *= 'ZoomBar'] img[id *= '"+buttonName+"']"));
	}
	
	@FindBy(how =How.CSS, using = "div[style *='zoombar.png']")
	public WebElement zoomBarOnMap;
	
	//Here I am using XPATH because I have to navigate to parent from child which is not possible to do with CSS
	@FindBy(how =How.XPATH, using = "//*[local-name()='svg' and @viewBox]/parent::div[contains(@id, 'RootContainer')]")
	public WebElement googleMapContainer;
	
	@FindBy(how =How.ID, using = "popup_contentDiv")
	public WebElement popupOnMap;
	
	@FindBy(how =How.ID, using = "popup_close")
	public WebElement closePopupOnMap;
	
	//@FindBy(how =How.XPATH, using = "//img[contains(@src, 'cameras')]/preceding-sibling::label")
	@FindBy(how =How.TAG_NAME, using = "label")
	public WebElement popupDescriptionOnInsideFrameInMap;
	
	public WebElement getPopupByTitle(WebDriver driver, String title) {
		return driver.findElement(By.xpath("//span[contains(@class, 'ui-dialog-title') and text() = '"+title+"']"));
	}
	
	@FindBy(how =How.CSS, using = "span.popuptitle+span")
	public WebElement popupHeader;
	
	public List<WebElement> getLinksInInformationPopupCorrespndingToCategory(WebDriver driver, String category){
		return driver.findElements(By.xpath("//div[@class='popup_menu_panel']/span[text()='"+category+"']/following-sibling::span[1]//a"));
	}
	
}
