package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * This class contains {@link WebElement} or locators of other pages 
 * which is reached after clicking on any link
 * 
 * @author Nitish
 * */
public class NavigationPages {

	//Sign in page container
	@FindBy(how = How.CLASS_NAME, using = "signin_wrapper")
	public WebElement signInContainer;
	
	/**
	 * This method will return a {@link WebElement} of a page heading
	 * 
	 * @author Nitish
	 * @param driver {@link WebDriver} instance
	 * @param heading of page to verify
	 * 
	 * */
	public WebElement getPageWithHeading(WebDriver driver, String heading) {
		return driver.findElement(By.xpath("//h5[text() = '"+heading+"']"));
	}
	
	@FindBy(how = How.CSS, using = "a[title = 'Google Play Logo']")
	public WebElement googlePlayLogo;
			
}
