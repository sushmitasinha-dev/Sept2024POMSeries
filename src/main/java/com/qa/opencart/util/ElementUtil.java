package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	// Construction of ElementUtil to initialize the WebDriver. Whenever user call
	// this class to use the functions , they need to provide the driver
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
		
	}

	// Function to null check the string
	private void nullCheck(CharSequence... value) {

		if (value == null) {

			throw new RuntimeException("============Value/property/Attribute cannot be null===========");

		}

	}
	
	private void highlightElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	// Function to create WebElement from the BY locator
	@Step("get WebElement using locator : {0}")
	public WebElement getElement(By locator) {
		
		WebElement element = driver.findElement(locator);
		highlightElement(element);
      	return element;

	}

	// Used when findElement return more than one element

	public List<WebElement> getElements(By locator) {

		return driver.findElements(locator);

	}

	// Function to perform SendKey Functionality
	@Step("fill Value : {1} into webelemnet : {0}")
	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);

	}
	@Step("fill Value : {1} into webelemnet : {0}")
	public void doSendKeys(WebElement element, CharSequence... value) {
		nullCheck(value);
		element.clear();
		element.sendKeys(value);

	}

	// Overload the DoSendKey : send the locator type and locator value
	// getElement(getLocator(locatorType ,locatorValue))
	// getLocator with get the by locator and give it to GetElement

	public void doSendKeys(String locatorType, String locatorValue, String value) {

		nullCheck(value);
		WebElement element = getElement(getLocator(locatorType, locatorValue));
		element.clear();
		element.sendKeys(value);
	}

	// Function to perform click

	
	@Step("Clicking on WebElement using Locator : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
		;

	}

	// Overload the doClick : send the locator type and locator value
	// getElement(getLocator(locatorType ,locatorValue))
	// getLocator with get the by locator and give it to GetElement

	public void doClick(String locatorType, String locatorValue) {
		getElement(getLocator(locatorType, locatorValue)).click();
	}

	// Function to Get the Text of the Specific Element

	public String doElementGetText(By locator) {

		return getElement(locator).getText();

	}

	public String doGetDomAttributes(By locator, String attrName) {

		nullCheck(attrName);
		return getElement(locator).getDomAttribute(attrName);

	}

	public String doGetDomProperty(By locator, String proprtyName) {

		nullCheck(proprtyName);
		return getElement(locator).getDomProperty(proprtyName);

	}

	// Functions to get the By Locator .. We will pass By Locator type(eg , id ,
	// name ..) and the value in the locator in the form the String

	public By getLocator(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		default:
			System.out.println("Invalid Locator");
			break;

		}
		return locator;
	}

	// Checks if the first element is present in the page or not along with
	// exception handling of element is not present

	@Step("Element {0} is diplayed")
	public boolean doIsElementDisplayed(By locator) {

		try {

			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed");
			return false;
		}

	}

	// Checks if the first element is present in the page, it will not give
	// exceptions if Elements is not present

	public Boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("Elements is available on the page one time ");
			return true;
		} else {
			System.out.println("Elements  is not displayed on the page");
			return false;
		}
	}

	// Check if element is present in the page with more than n number of times

	public Boolean isElementDisplayed(By locator, int eleCount) {
		if (getElements(locator).size() == eleCount) {
			System.out.println("Elements is available on the page  " + eleCount + "   times");
			return true;
		} else {
			System.out.println("Elements  is not displayed on the page");
			return false;
		}
	}

	// ========================================Select Drop Down  Utilites===============================================

	public void doSelectDropDownByIndex(By locator, int index) {

		Select select = new Select(getElement(locator));
		select.selectByIndex(5);
	}

	public void doSelectDropDownByValue(By locator, String value) {

		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public int getDropDownOptionsCount(By locator) {

		Select select = new Select(getElement(locator));
		List<WebElement> countryOptionsList = select.getAllSelectedOptions();

		System.out.println("Option Size " + countryOptionsList.size());
		return countryOptionsList.size();

	}

	public List<String> getDropDownOptionsTextList(By locator) {

		Select select = new Select(getElement(locator));
		List<WebElement> optionList = select.getAllSelectedOptions();

		System.out.println("Option Size " + optionList.size());
		List<String> optionValueList = new ArrayList<String>();
		for (WebElement e : optionList) {
			String text = e.getText();
			optionValueList.add(text);
		}
		return optionValueList;

	}

	public void printDropDownOptionsTextList(By locator) {

		Select select = new Select(getElement(locator));
		List<WebElement> optionList = select.getAllSelectedOptions();
		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);
		}

		System.out.println("------------------End of the List ----------------------------");

	}

	public void selectValueFromDropDown(By locator, String value) {

		Select select = new Select(getElement(locator));

		List<WebElement> options = select.getOptions();
		boolean flag = false;
		for (WebElement e : options) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				flag = true;
				break;

			}
			if (flag) {
				System.out.println(value + " is available and selected  ");

			} else {
				System.out.println(value + " is not available  ");
			}
		}

	}

//---------------------ACTIONS UTILTIES ------------------------------------------------------------------------------

	public void doActionsSendKeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {

		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();

	}

	public void handleTwoLevelMenuHandling(By parentMenuLoc, By childMenuloc) throws InterruptedException {

		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLoc)).build().perform();
		Thread.sleep(2000);
		getElement(childMenuloc).click();

	}

	public void Level4MenuSubMenuHandling(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		Actions act = new Actions(driver);

		getElement(level1Menu).click();
		Thread.sleep(2000);

		act.moveToElement(getElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		getElement(level4Menu).click();

	}

	public void doSendKeysWithPaused(By locator, String value, long pausedTime) {

		Actions act = new Actions(driver);
		char val[] = value.toCharArray();

		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(500).perform();
		}

	}

	// --------------------------------------------Wait Utils-----------------------------------------------//

	// **********************************Wait for  Elements***************************************************

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * Thsi does not mean element is visible on page
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitforElementPresence(By locator, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible Visibility means that the element is not only displayed but also
	 * has a height and width that is grater than 0
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 **/
	@Step("Waiting for WebElement by Locator  : {0} within timeout {1}")
	public WebElement waitforElementVisible(By locator, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;

	}

	public WebElement waitforElementVisible(By locator, long timeout, long pollingTime) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;

	}

	public List<WebElement> waitForElementsPresence(By locator, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e ) {
			return Collections.emptyList();
		}

	}

	public List<WebElement> waitForElementsVisible(By locator, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e ) {
			return Collections.emptyList();
		}

	}

	public void clickElementWhenReady(By locator, long timout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	// *****************************************wait for Title ********************************************************

	/**
	 * 
	 * @param fractionTitle
	 * @param timeout
	 * @return
	 */
	public String waitForTitleContains(String fractionTitle, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeout + " seconds");

		}
		return null;

	}

	/**
	 * @param title
	 * @param timeout
	 * @return
	 */
    @Step("waitForTitle IS : {0} within timeout : {1}" )
	public String waitForTitleIs(String title, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeout + " seconds");

		}
		return null;

	}

	public Boolean isPageLoaded(long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String isPageLoaded = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"))
				.toString();
		System.out.println(isPageLoaded);
		return Boolean.parseBoolean(isPageLoaded);

	}

	// ***********************************************Wait for URL***********************************************
	@Step("Wait for URL : {0} within timoeour {1}")
	public String waitForURLContains(String fractionURL, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			if (wait.until(ExpectedConditions.urlContains(fractionURL))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not found after " + timeout + " seconds");

		}

		return null;

	}

	public String waitForURLToBe(String url, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not found after " + timeout + " seconds");

		}

		return null;

	}

	// ******************************************Wait for Alerts *******************************************************
	public Alert waitForAlert(long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(long timout) {
		return waitForAlert(timout).getText();

	}

	public void acceptAlert(long timout) {
		waitForAlert(timout).accept();
	}

	public void dismisstAlert(long timout) {
		waitForAlert(timout).dismiss();
	}

	public void alertSendKeys(String text, long timout) {
		waitForAlert(timout).sendKeys(text);
		;
	}

	// ************************* wait for IFRAMEs*************************************************:

	public void waitForIFrameByIndexAndSwitch(By frameIndex, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	public void waitForIFrameByLocatorAndSwitch(String frameIDOrName, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));

	}

	public void waitForIFrameByLocatorAndSwitch(WebElement frameElement, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public void waitForIFrameByLocatorAndSwitchToIt(By frameLocator, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	// ********************************Wait For Window  *******************************************

	public Boolean waitForWindow(int numberOfWindow, long timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindow));
		} catch (TimeoutException e) {
			System.out.println("The windows are not available ");
			return false;
		}

	}

}
