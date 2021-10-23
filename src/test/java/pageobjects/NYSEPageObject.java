package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NYSEPageObject {

	@FindBy(how =How.ID, using = "page-search")
	public WebElement searchTB;
	
	@FindBy(how =How.CSS, using = "div#search-results a")
	public List<WebElement> searchResults;
	
	@FindBy(how =How.XPATH, using = "(//div[@id='search-results']//a)[1]")
	public WebElement firstSearchResult;
	
	//Chart page
	@FindBy(how =How.XPATH, using = "(//div[contains(@class, 'Chart-nyse')]//canvas)[1]")
	public WebElement priceChart;
	
	@FindBy(how =How.XPATH, using = "//table[@class='-d-chart-header']")
	public WebElement priceDetailsTable;
		
	public WebElement getChartValueAsPerLabel(WebDriver driver, String label) {
		return driver.findElement(By.xpath("//table[@class='-d-chart-header']//td[.='"+ label+ "']/following-sibling::td"));
	}
	
}
