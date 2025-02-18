package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;

public class CommonsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	public CommonsPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
		
	}
	
	private By logo = By.className("img-responsive");
	private By footer = By.xpath("//footer//a");
	
	public boolean isLogoDisplayed() {
		
		return eleUtil.doIsElementDisplayed(logo);
	}
	
	public List<String> getFooterList() {
		 
		List<WebElement> footerList = eleUtil.waitForElementsPresence(footer, AppsConstants.DEFAULT_TIME_OUT);
		System.out.println("Total nuber of footer : " + footerList.size());
		List<String> footers = new ArrayList<String>();
		for(WebElement e :footerList ) {
			String text = e.getText();
			footers.add(text);
			
		}
		return footers;
		
		
	}
	
	public boolean checkFooterLink(String footerLink) {
		
		return getFooterList().contains(footerLink);
	}

}
