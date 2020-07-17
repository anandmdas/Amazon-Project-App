package amazonAutomation.Model;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporting;
import utils.WrapperTest;

public class CartPage extends WrapperTest{

	Properties prop;
	public CartPage() {
		prop=new Properties();
		try {
			prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PageObjectProperty\\cartPage.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Description: Method to compare the products in cart
	 * Created By: Anand Mohandas 
	 */
	
	public void compareProductDetails(Reporting report,String prodName) throws Exception
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("productName"))));
		
		String uiProdName=getText("xpath", prop.getProperty("productName"), report);
		System.out.println("uiProdName"+uiProdName);
		StringTokenizer st=new StringTokenizer(uiProdName, "...");
		
		System.out.println("prodName"+prodName);
		System.out.println("uiProdName"+uiProdName);
		stringContains(st.nextToken(),prodName, report);
		String elemCount=getText("xpath", prop.getProperty("productCount"), report);
		stringContains(elemCount, "1", report);
		
	}
	
	public void navigateToCheckout(Reporting report) throws Exception
	{
		click("xpath", prop.getProperty("checkOutBtn"), report);
	}
}
