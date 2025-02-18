package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppsConstants;
import com.qa.opencart.expections.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	public WebDriver initDriver(Properties prop) {
		System.out.println(prop.getProperty("browser"));
		System.out.println(prop.getProperty("url"));
		String browserName = prop.getProperty("browser");
		//System.out.println(" ======The Browser Name ====" + browserName);
		log.info("Browser Name is : " + browserName );
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "edge":
			
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		case "Safari":
			
			tlDriver.set(new SafariDriver());
			//driver = new SafariDriver();
			break;

		default:
			//System.out.println("Please enter correct Browser Name" + browserName);
			log.error("Please enter correct Browser Name" + browserName);
			throw new FrameworkException("Invalid Browser Name");
			
		}
		
	    getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		getDriver().get(prop.getProperty("url"));
		return getDriver();

		
	}
	

	
	// get driver using threadlocal
	
	public static WebDriver getDriver() {
		
		return tlDriver.get();
		//return driver;
		
		
	}
    
	// This method is used to init the properties from .preperties file 
	
	// supply env name from maven command line
	//maven clean install -Denv = 'qa'
	
	public Properties initProp()  {
		String envName =System.getenv("env");
		System.out.println("running test suite on env : " + envName);
		prop = new Properties();
		FileInputStream ip = null;
		try {
		if(envName == null) {
			System.out.println("no env is passed , hence running test suite on qa env..");
			log.warn("no env is passed , hence running test suite on qa env..");
			ip = new FileInputStream(AppsConstants.CONFIG_QA_PROP_FILE_PATH);
		}else {
			switch(envName.trim().toLowerCase()) {
			case "qa":
				ip = new FileInputStream(AppsConstants.CONFIG_QA_PROP_FILE_PATH);
				break;
			case "dev":
				ip = new FileInputStream(AppsConstants.CONFIG_DEV_PROP_FILE_PATH);
				break;
			case "uat":
				ip = new FileInputStream(AppsConstants.CONFIG_UAT_PROP_FILE_PATH);
				break;
			case "stage":
				ip = new FileInputStream(AppsConstants.CONFIG_STAGE_PROP_FILE_PATH);
				break;
			case "prod":
				ip = new FileInputStream(AppsConstants.CONFIG_PROP_FILE_PATH);
				break;
			default:
				log.error("Please pass the corrert env name " + envName);
				throw new FrameworkException("====invalid env=======");
				
			}
		}
		
		
		
		prop.load(ip);
		
		}catch(FileNotFoundException e ) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	
	/** 
	 * taking Screenshot
	 */
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/"  + "_" + System.currentTimeMillis()+ ".png";		
		File destination = new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
		
	}
	
	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// return as Byte 
		
		
	}
	
	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir
		
		
	}
	

}
