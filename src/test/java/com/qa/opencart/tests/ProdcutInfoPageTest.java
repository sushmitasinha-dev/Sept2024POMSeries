package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

public class ProdcutInfoPageTest extends BaseTest {

	
	@BeforeClass
	public void productInfoSetUP() {
		
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
		
	}
	
	@Test(dataProvider = "getProductData")
	public void productSearchHeaderTest(String searchKey , String productName) {
		ChainTestListener.log(searchKey + ": " + productName);
		searchResultPage = homePage.doSearch(searchKey);
		prodcutInfoPage = searchResultPage.selectTheProduct(productName);
		String actualProductHeader = prodcutInfoPage.getProductHeader();
		Assert.assertEquals(actualProductHeader, productName );
		
	}
	
	@Test(dataProvider = "getProductImageData")
	public void productImagesCount(String searchKey , String productName , int ExpectedImageCount) {
		searchResultPage = homePage.doSearch(searchKey);
		prodcutInfoPage = searchResultPage.selectTheProduct(productName);
		int actualProductImages = prodcutInfoPage.getProductImagesCount();
		Assert.assertEquals(actualProductImages, ExpectedImageCount);
		
	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] {
			{"macbook" , "MacBook Pro" , 4},
			{"macbook" , "MacBook Air" , 4},
			{"imac" , "iMac" , 3},
			{"samsung" , "Samsung SyncMaster 941BW" , 1},
			{"Samsung" , "Samsung Galaxy Tab 10.1" , 7}
			
		};
	}
	
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook" , "MacBook Pro"},
			{"imac" , "iMac"},
			{"samsung" , "Samsung SyncMaster 941BW"}
			
		};
	}
	
	@Test
	public void ProductInfoTest() {
		searchResultPage = homePage.doSearch("macbook");
		prodcutInfoPage = searchResultPage.selectTheProduct("MacBook Pro");
		Map<String , String > ProductInfoMap = prodcutInfoPage.getProductInfo();
		ProductInfoMap.forEach((k,v) -> System.out.println(k + ": " + v));
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(ProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(ProductInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(ProductInfoMap.get("Product Code "), "Product 18");
		softAssert.assertEquals(ProductInfoMap.get("Rewards Points"), "800");
		
		softAssert.assertEquals(ProductInfoMap.get("price"), "$2000.00");
		softAssert.assertEquals(ProductInfoMap.get("extax"), "$2000.00");
		
	}
	
	@Test
	public void addToCartTest() {
		searchResultPage = homePage.doSearch("macbook");
		prodcutInfoPage = searchResultPage.selectTheProduct("MacBook Pro");
		String text = prodcutInfoPage.addProductToCart();
		Assert.assertEquals(text, "MacBook Pro", "Product is not added successfully");
	}
}
