package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.JavaScriptUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	public LoginPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
    private By emailId = By.id("input-email");
    private By password= By.id("input-password");
    private By loginBtn = By.xpath("//input[@class = 'btn btn-primary']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    
    @Step("getLoginPageTitle")
    public String getLoginPageTitle() {
    	String title = eleUtil.waitForTitleIs(AppsConstants.LOGIN_PAGE_TITLE, AppsConstants.DEFAULT_TIME_OUT);
    	System.out.println("The Title of the Page : " + title );
    	ChainTestListener.log("The Title of the Page : " + title);
    	return title ;
    }
    
    @Step("getLoginPageURL")
    public String getLoginPageURL() {
    	String currentURL = eleUtil.waitForURLContains(AppsConstants.LOGIN_PAGE_URL_FRACTION, AppsConstants.DEFAULT_TIME_OUT);
    	System.out.println("The URL of the Page is  : " + currentURL);
    	return currentURL;
    	
    }
    
    @Step("Checking forgot password link is displayed")
    public boolean isForgetPwdLinkExists() {
    	
    	return eleUtil.doIsElementDisplayed(forgotPwdLink);
    }
    
    @Step("Logging with username : {0} and password : {1}")
    public HomePage doLogin(String userName , String pwd) {
    	System.out.println("Apps Creds are : ======" + userName + "    : " + pwd);
    	eleUtil.waitforElementVisible(emailId,AppsConstants.SHORT_TIME_OUT).sendKeys(userName);
    	eleUtil.doSendKeys(password, pwd);
    	eleUtil.doClick(loginBtn);
    	return new HomePage(driver);    	
    }
	

}
