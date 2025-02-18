package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productResults = By.cssSelector("div.product-thumb");
	
	public int getProductResultsCount() {
		
		int resultCount = eleUtil.waitForElementsPresence(productResults, AppsConstants.DEFAULT_TIME_OUT).size();	
		System.out.println("The Product result count : "+ resultCount );
		return resultCount;
		
	}
	
	public ProductInfoPage selectTheProduct(String ProductName) {
		
		System.out.println("The Product Name : " + ProductName );
		eleUtil.doClick(By.linkText(ProductName));
		//driver.findElement(By.linkText(ProductName)).click();
		return new ProductInfoPage(driver);
		
	}
	
}
