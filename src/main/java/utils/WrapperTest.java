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
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class WrapperTest {

	public static AndroidDriver<AndroidElement>driver;
	public DesiredCapabilities capb;
	Properties prop;
	
	public WrapperTest() {
		capb=new DesiredCapabilities();
		prop=new Properties();
	}
	
	/*
	 * Description: Reusable function initialize the driver
	 * Created By: Anand Mohandas 
	 */

	public void driverInit() throws IOException
	{
		prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PropertyFiles\\capability.properties");
		
		capb.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("deviceName"));
		capb.setCapability("platformName", prop.getProperty("platformName"));
		capb.setCapability("app", prop.getProperty("app"));
		capb.setCapability("appActivity", prop.getProperty("appActivity"));
		capb.setCapability("appPackage", prop.getProperty("appPackage"));
		
		driver=new AndroidDriver<AndroidElement>(new URL(prop.getProperty("hubUrl")),capb);
	}
	
	
	/*
	 * Description: Reusable function to load the property File
	 * Created By: Anand Mohandas 
	 */
	public Properties loadPropertyFile(String path) throws IOException
	{
		
		FileInputStream fs=new FileInputStream(path);
		prop.load(fs);
		return prop;
	}
	
	/*
	 * Description: Reusable function to click on an element
	 * Created By: Anand Mohandas 
	 */
	
	public void click(String elementType,String identifier,Reporting report) throws Exception
	{
		if(elementType.equalsIgnoreCase("id"))
			driver.findElement(By.id(identifier)).click();
		else if(elementType.equalsIgnoreCase("xpath"))
			driver.findElement(By.xpath(identifier)).click();
		
		report.extentReportPass(identifier+ "is clicked");
	}
	
	/*
	 * Description: Reusable function to input value in a text field
	 * Created By: Anand Mohandas 
	 */
	
	
	public void inputValue(String elementType,String identifier,Reporting report, String value) throws Exception
	{
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
	
	/*
	 * Description: Reusable function to select a random object from a list
	 * Created By: Anand Mohandas 
	 */
	
	
	public void selectRandomValue(String elementType,String identifier,Reporting report)
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
	
	/*
	 * Description: Reusable function to fetch the text from an object
	 * Created By: Anand Mohandas 
	 */
	
	public String getText(String elementType,String identifier,Reporting report)
	{
		String text="";
		if(elementType.equalsIgnoreCase("id"))
			text=driver.findElement(By.id(identifier)).getText();
		else if(elementType.equalsIgnoreCase("xpath"))
			text=driver.findElement(By.xpath(identifier)).getText();
		
		report.extentReportPass(text+" is retrieved from "+identifier);
		
		return text;
	}
	
	/*
	 * Description: Reusable function to check an element is present in UI
	 * Created By: Anand Mohandas 
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
}
