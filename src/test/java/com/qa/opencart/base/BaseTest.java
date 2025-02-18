package com.qa.opencart.base;

import java.util.Properties;


import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultPage;



//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	 WebDriver driver;
	DriverFactory df;
	
	
	protected Properties prop;
	
	
	protected LoginPage loginPage ;
	protected HomePage homePage ;
	protected SearchResultPage searchResultPage ;
	protected ProductInfoPage prodcutInfoPage ;
	protected CommonsPage commanPage;
	
	
	@BeforeTest
	public void setup() {
		df = new DriverFactory();
		prop = df.initProp();
		//prop = new Properties();
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		//homePage = new HomePage(driver);	
		commanPage = new CommonsPage(driver);
		
		
		/*
		 * * To get System Properties in the report 
		 */
		ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
		ChainPluginService.getInstance().addSystemInfo("Headless#", prop.getProperty("headless"));
	    ChainPluginService.getInstance().addSystemInfo("Incognito#", prop.getProperty("incognito"));
	    ChainPluginService.getInstance().addSystemInfo("Owner#", "Naveen Automation Labs");
		
	}
	
	@AfterMethod
	public void attachSecrrenshot(ITestResult result) {
		
		if(!result.isSuccess()) { // only for Failure Testcases
		//ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
			ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
		}
	}
	
	@AfterTest
	public void tearDown() {
		
		//driver.quit();
		
	}

}
