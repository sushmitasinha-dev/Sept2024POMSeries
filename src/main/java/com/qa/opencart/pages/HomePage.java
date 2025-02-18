package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;

public class HomePage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public HomePage(WebDriver driver ) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	// 1. By Locator
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content>h2");	
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	//2 Public page actions
	
	public String getHomePageTitle() {
		String title  = eleUtil.waitForTitleIs(AppsConstants.HOME_PAGE_TITLE, AppsConstants.DEFAULT_TIME_OUT);
		System.out.println("The Title of the Home Page : " + title);
		return title;
	}
	
	public String getHomePageURL() {
		String pageURL = eleUtil.waitForURLContains(AppsConstants.HOME_PAGE_URL_FRACTION, AppsConstants.DEFAULT_TIME_OUT);
		return pageURL;
	}
	
	public boolean isLogoutLinkExist() {
		
		return eleUtil.doIsElementDisplayed(logoutLink);
	}

	public List<String> getHomePageHeadersList() {
		
		List<WebElement> headerslist = eleUtil.waitForElementsVisible(headers, AppsConstants.DEFAULT_TIME_OUT);
		List<String> headerValList = new ArrayList<String>();
		for(WebElement e : headerslist) {
			String text = e.getText();
			headerValList.add(text);
		}
		
		return headerValList;
		
		 
	}
	//pendig WIP
	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);;
		}
	}
	
	public  SearchResultPage doSearch(String searchKey) {
		
		System.out.println("The Search Key Provided is :  "  +searchKey ); 
		WebElement searchEle = eleUtil.waitforElementVisible(search, AppsConstants.SHORT_TIME_OUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultPage(driver);

	
	}
}
