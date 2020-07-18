package utils;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Utility {

	public static AndroidDriver<AndroidElement>driver;
	public DesiredCapabilities capb;
	Properties prop;

	public Utility() {
		capb=new DesiredCapabilities();
		prop=new Properties();
	}

	/*
	 * Description: Reusable function initialize the driver
	 * Created By: Anand Mohandas 
	 */

	public void driverInit(Reporting extentReport) 
	{
		try
		{

			prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PropertyFiles\\capability.properties");

			capb.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("deviceName"));
			capb.setCapability("platformName", prop.getProperty("platformName"));
			capb.setCapability("app", prop.getProperty("app"));
			capb.setCapability("appActivity", prop.getProperty("appActivity"));
			capb.setCapability("appPackage", prop.getProperty("appPackage"));

			driver=new AndroidDriver<AndroidElement>(new URL(prop.getProperty("hubUrl")),capb);
		}
		catch (Exception e) {
			e.printStackTrace();
			extentReport.extentReportFail(e.getMessage());
			Assert.assertTrue(false, e.getMessage());

		}
	}


	/*
	 * Description: Reusable function to load the property File
	 * Created By: Anand Mohandas 
	 * Attribute: path- Path of the property file
	 */
	public Properties loadPropertyFile(String path)
	{
		try
		{
			FileInputStream fs=new FileInputStream(path);
			prop.load(fs);
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false, e.getMessage());
		}
		return prop;
	}

	/*
	 * Description: Reusable function to click on an element
	 * Created By: Anand Mohandas 
	 * Attribute: elementType- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 * 			  report- Class object of Reporting to generate extent report
	 */

	public void click(String elementType,String identifier,Reporting report) 
	{
		try
		{
			if(elementType.equalsIgnoreCase("id"))
				driver.findElement(By.id(identifier)).click();
			else if(elementType.equalsIgnoreCase("xpath"))
				driver.findElement(By.xpath(identifier)).click();

			report.extentReportPass(identifier+ "is clicked");
		}
		catch (Exception e) {
			e.printStackTrace();
			report.extentReportFail(e.getMessage());
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * Description: Reusable function to input value in a text field
	 * Created By: Anand Mohandas 
	 * Attribute: elementType- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 * 			  report- Class object of Reporting to generate extent report
	 * 			  value- String value to be inserted in the text field
	 */


	public void inputValue(String elementType,String identifier,Reporting report, String value)
	{
		try {
			if(elementType.equalsIgnoreCase("id"))
			{
				driver.findElement(By.id(identifier)).click();
				driver.findElement(By.id(identifier)).clear();
				driver.findElement(By.id(identifier)).sendKeys(value);

				report.extentReportPass(identifier+ "is inputed with "+value);
			}
			else if(elementType.equalsIgnoreCase("xpath"))
			{
				driver.findElement(By.xpath(identifier)).click();
				driver.findElement(By.xpath(identifier)).clear();
				driver.findElement(By.xpath(identifier)).sendKeys(value);

				report.extentReportPass(identifier+ "is inputed with "+value);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			report.extentReportFail(e.getMessage());
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * Description: Reusable function to select a random object from a list
	 * Created By: Anand Mohandas 
	 * Attribute: elementType- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 * 			  report- Class object of Reporting to generate extent report
	 */


	public void selectRandomValue(String elementType,String identifier,Reporting report)
	{
		try
		{
			int size=0;
			Random rand = new Random();
			if(elementType.equalsIgnoreCase("id"))
			{
				size=driver.findElements(By.id(identifier)).size();
				int num=rand.nextInt(size);

				driver.findElements(By.id(identifier)).get(num).click();
				report.extentReportPass("Random option is selected");
			}
			else if(elementType.equalsIgnoreCase("xpath"))
			{
				size=driver.findElements(By.xpath(identifier)).size();
				int num=rand.nextInt(size);

				driver.findElements(By.xpath(identifier)).get(num).click();
				report.extentReportPass("Random option ("+num+") is selected");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			report.extentReportFail(e.getMessage());
		}
	}

	/*
	 * Description: Reusable function to fetch the text from an object
	 * Created By: Anand Mohandas 
	 * Attribute: elementType- lement type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 * 			  report- Class object of Reporting to generate extent report
	 */

	public String getText(String elementType,String identifier,Reporting report)
	{

		String text="";
		try
		{
			if(elementType.equalsIgnoreCase("id"))
				text=driver.findElement(By.id(identifier)).getText();
			else if(elementType.equalsIgnoreCase("xpath"))
				text=driver.findElement(By.xpath(identifier)).getText();

			report.extentReportPass(text+" is retrieved from "+identifier);
		}
		catch (Exception e) {
			e.printStackTrace();
			report.extentReportFail(e.getMessage());
		}
		return text;
	}

	/*
	 * Description: Reusable function to check an element is present in UI
	 * Created By: Anand Mohandas 
	 * Attribute: property- xpath value of the element to be identified
	 */

	public boolean verifyElement(String property) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);



		boolean present = true;
		try {
			driver.findElement(By.xpath(property));
			return present;

		} catch (Exception e) {
			present = false;
			e.printStackTrace();
			return present;
		}



	}

	/*
	 * Description: Reusable function to scroll and check an element is present in UI
	 * Created By: Anand Mohandas 
	 * Attribute: property- xpath value of the element to be identified
	 */
	public void swipeToElement(String property) {
		int count=0;
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			swipeFullFromTopToBottom();
			count++;
			if(count>10)
				Assert.assertTrue(false, "Element Not Visible");
		}
	}



	/*
	 * Description: Reusable function to scroll from Top to bottom
	 * Created By: Anand Mohandas 
	 */

	public void swipeFullFromTopToBottom() {



		try {
			Thread.sleep(2000);
			Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int endy = (int) (scrnSize.height*0.9);
			int starty = (int) (scrnSize.height * 0.2);


			(new TouchAction(driver))
			.press(startx, endy)
			.moveTo(startx,starty)
			.release()
			.perform();



		} catch (InterruptedException e) {
			Assert.assertTrue(false,e.getMessage());
		}



	}

	/*
	 * Description: Reusable function to compare 2 strings
	 * Created By: Anand Mohandas 
	 * Attribute: smallString-String value to be compared with
	 * 			  bigString- String value to be compared in
	 * 			  report- Class object of the Reporting class for generating extent report
	 */

	public void stringContains(String smallString,String bigString,Reporting report)
	{
		if(bigString.contains(smallString))
		{
			Assert.assertTrue(true, "String comparison Successful");
			report.extentReportPass("String compared successfully");
		}
		else
		{
			Assert.assertTrue(false, "String comparison Failed expected:"+bigString+" contains "+smallString );
			report.extentReportFail("String comparison failed");
		}
	}

	/*
	 * Description: Reusable function to wait for an element to be clickable
	 * Created By: Anand Mohandas 
	 * Attribute: elementType- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 */

	public void waitForElementToBeClickable(String elementType,String identifier)
	{
		try
		{
			WebDriverWait wait=new WebDriverWait(driver, 60);
			if(elementType.equalsIgnoreCase("id"))
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
			else if(elementType.equalsIgnoreCase("xpath"))
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, e.getMessage());
		}
	}
}
