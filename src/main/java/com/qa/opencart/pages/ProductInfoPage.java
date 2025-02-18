package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {
	
      private WebDriver driver;
 	  private ElementUtil eleUtil;
 	  private Map<String , String> productMap;
	
	 public ProductInfoPage(WebDriver driver) {
	 	this.driver = driver;
	 	eleUtil = new ElementUtil(driver);
	}
	 
	 private By productHeader = By.tagName("h1");
	 private By productImages = By.cssSelector("ul.thumbnails img");
	 private By productMetaData = By.xpath("(//div[@id = 'content']//ul[@class = 'list-unstyled'])[1]/li");
	 private By productPriceData = By.xpath("(//div[@id = 'content']//ul[@class = 'list-unstyled'])[2]/li");
	 private By productQuantity = By.id("input-quantity");
	 private By addToCartbtn = By.id("button-cart");
	 private By productAddMsg = By.xpath("(//div[@class= 'alert alert-success alert-dismissible']/a)[1]");
	 private By shoppingCartLink = By.linkText("shopping cart");
	 
	 public String getProductHeader() {
		 
		 String header=  eleUtil.doElementGetText(productHeader);
		 System.out.println("Product Header : ====> " + header);
		 return header;
	 }

	 
	 public int getProductImagesCount() {
		 int imagesCount= eleUtil.waitForElementsPresence(productImages, AppsConstants.DEFAULT_TIME_OUT).size();
		 System.out.println(getProductHeader() + ": Images Count "+ imagesCount);
		 return imagesCount;
	 }
	 
	 
	 // Get the Full product information
	 
	 public Map<String , String> getProductInfo() {
		// productMap = new HashMap<String , String>();
		//productMap = new TreeHashMap<String , String>();  // Keys are sorted
		 productMap = new LinkedHashMap<String , String>(); // Index is maintained
		 productMap.put("Header", getProductHeader());
		 productMap.put("ImagesCount", getProductImagesCount()+"");
		 getProductMetaData();
		 getProductPriceList();
		 return productMap;
		 
	 }
	 
	 
	 
	 private void getProductMetaData() {
		 
		 List<WebElement> metaList = eleUtil.waitForElementsPresence(productMetaData, AppsConstants.DEFAULT_TIME_OUT);
		 for(WebElement e :metaList) {
			 String metaText = e.getText();
			 String meta[] = metaText.split(":");
			 String metaKey = meta[0].trim();
			 String metaValue = meta[1].trim();
			 productMap.put(metaKey, metaValue);
			 
					 
		 }
		 
	 }
	 
	 private void getProductPriceList() {
		 List<WebElement> priceList = eleUtil.waitForElementsPresence(productPriceData, AppsConstants.DEFAULT_TIME_OUT);
		 String productPrice = priceList.get(0).getText().trim();
		 String prodcutExTax = priceList.get(1).getText().split(":")[1].trim();
		 productMap.put("Price", productPrice);
		 productMap.put("exTax", prodcutExTax);
	 }
	 
	 public String addProductToCart() {
		  
		 WebElement productQuantityEle = eleUtil.waitforElementVisible(productQuantity, AppsConstants.DEFAULT_TIME_OUT);
		 eleUtil.doSendKeys(productQuantityEle, "1");
		 eleUtil.waitforElementVisible(addToCartbtn, AppsConstants.DEFAULT_TIME_OUT).click();
		 String text = eleUtil.waitforElementPresence(productAddMsg,AppsConstants.DEFAULT_TIME_OUT).getText();
		 System.out.println(text);
		 return text;
		 
		 
	 }
	 
	 public ShoppingCartPage doClickShoppingCart() {
		 
		WebElement shoppingCartLinkEle =  eleUtil.waitforElementPresence(shoppingCartLink, AppsConstants.DEFAULT_TIME_OUT);
		shoppingCartLinkEle.click();
		return new ShoppingCartPage(driver);
		
		
		 
		 
	 }
}
