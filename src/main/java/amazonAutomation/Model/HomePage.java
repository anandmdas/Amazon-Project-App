package amazonAutomation.Model;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ReadExcel;
import utils.Reporting;
import utils.WrapperTest;

public class HomePage extends WrapperTest {

	Properties prop;
	public HomePage() {
		// TODO Auto-generated constructor stub
		
		prop=new Properties();
		try {
			prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PageObjectProperty\\homePage.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Description: Method to Search a Product
	 * Created By: Anand Mohandas 
	 */
	
	public void searchProduct(Reporting report, ReadExcel readExcel) throws Exception
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("searchBox"))));
		
		click("id", prop.getProperty("searchBox"), report);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("searchBox"))));
		
		inputValue("id", prop.getProperty("searchBox"), report, readExcel.getValue("SearchText"));
		
		Thread.sleep(3000);
		selectRandomValue("xpath", "//*[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions']", report);
	
	}
	
	/*
	 * Description: Method to select a searched product
	 * Created By: Anand Mohandas 
	 */
	
	public void selectSearchProduct(Reporting report)
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(prop.getProperty("productCount"))));
		
		selectRandomValue("xpath", prop.getProperty("productList"), report);
	}
}
