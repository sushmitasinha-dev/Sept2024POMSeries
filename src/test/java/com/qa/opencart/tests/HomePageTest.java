package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppError;
import com.qa.opencart.constants.AppsConstants;

public class HomePageTest extends BaseTest {
	
	
	@BeforeClass
	public void homePageSetup() {
		homePage =  loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
		
	}
	
	@Test
	public void homePageTitleTest() {
		String titleChk = homePage.getHomePageTitle();
		Assert.assertEquals(titleChk, AppsConstants.HOME_PAGE_TITLE ,AppError.TITLE_NOT_FOUND_ERROR);
		
	}
	
	@Test
	public void homePageURL() {
		String uRLChk = homePage.getHomePageURL();
		Assert.assertTrue(uRLChk.contains(AppsConstants.HOME_PAGE_URL_FRACTION) ,AppError.URL_NOT_FOUND_ERROR);
	}
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist() , AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	
	@Test
	public void headersTest() {
		List<String> actualHeaders = homePage.getHomePageHeadersList();
		System.out.println("Home Page Headers are :   " + actualHeaders);
		
	}
	
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey , int resultCount) {
		searchResultPage=  homePage.doSearch(searchKey);
	//	int count = searchResultPage.getProductResultsCount();
		Assert.assertEquals(searchResultPage.getProductResultsCount(), resultCount , "Count does not matched ");
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
				{"macbook" , 3},
				{"imac",1},
				{"samsung",2},
				{"canon" , 1},
				{"airtel",0}		
		};
		
	}
	
	@Test(description = "Checking Logo in Home Page" ,enabled = false)
	public void logoTest() {
		Assert.assertTrue(commanPage.isLogoDisplayed() , AppError.LOGO_NOT_FOUND_ERROR);
	}
	
	@DataProvider
	public Object[][] getFooterData() {
		return new Object[][] {
			{"About Us"},
			{"Contact Us"},
			{"Specials"},
			{"Order History"}
		};
		}

	
	@Test(dataProvider = "getFooterData" , description = "Checking Footer in Home Page" , enabled = false )
	public void footerTest(String footerLink) {
		Assert.assertTrue(commanPage.checkFooterLink(footerLink) );
	}

	
	
	

}
