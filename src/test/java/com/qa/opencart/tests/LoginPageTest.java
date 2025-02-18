package com.qa.opencart.tests;

import org.testng.Assert;
import com.aventstack.chaintest.plugins.ChainTestListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppError;
import com.qa.opencart.constants.AppsConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: design login page for OpenCart")
@Story("US 101: design the varoius features of open cart login page ")
@Feature("Feature 50: Login page Feature")

public class LoginPageTest extends BaseTest{
	
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("verifying login page Title");
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppsConstants.LOGIN_PAGE_TITLE , AppError.TITLE_NOT_FOUND_ERROR);
		
	}
	@Description("Checking the page login")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppsConstants.LOGIN_PAGE_URL_FRACTION) , AppError.URL_NOT_FOUND_ERROR);
		
	}
	@Description("Checking the Forgot owd Link--")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExist() {
		Assert.assertTrue(loginPage.isForgetPwdLinkExists() , AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	@Description("Checking User is able to login--")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), AppsConstants.HOME_PAGE_TITLE , AppError.TITLE_NOT_FOUND_ERROR);
		
	}
	@Description("Checking login page Logo--")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "Checking Logo in Login Page")
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

	@Description("Checking login page Footer-")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData" , description = "Checking Footer in Login Page" )
	public void footerTest(String footerLink) {
		Assert.assertTrue(commanPage.checkFooterLink(footerLink) );
	}

}
