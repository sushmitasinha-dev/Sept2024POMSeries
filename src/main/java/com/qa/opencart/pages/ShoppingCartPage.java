package com.qa.opencart.pages;

import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;

public class ShoppingCartPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver  = driver ;
		eleUtil = new ElementUtil(driver);
	}
	
	
	
	public String getShopingCartTitle() {
		
		String title = eleUtil.waitForTitleIs(AppsConstants.SHOPPING_CART_PAGE_TITLE, AppsConstants.DEFAULT_TIME_OUT);
		System.out.println("The Title of the page : " + title);
		return title;
	}
	
	public String getShoppingCartURL() {
		String currentURL = eleUtil.waitForURLContains(AppsConstants.SHOPPING_CART_PAGE_URL_FRACTION, AppsConstants.DEFAULT_TIME_OUT);
		System.out.println("The Current URL is : "+ currentURL);
		return currentURL;
		
	}

}
