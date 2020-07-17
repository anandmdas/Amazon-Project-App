package amazonAutomation.Model;


import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import utils.Reporting;
import utils.WrapperTest;

public class ProductsPage extends WrapperTest {

	Properties property;
	public ProductsPage() {
		// TODO Auto-generated constructor stub
		
		property=new Properties();
		try {
			property=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PageObjectProperty\\products.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Description: Method to get the product details
	 * Created By: Anand Mohandas 
	 */
	
	public String getProductDetails(Reporting report) throws Exception
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(property.getProperty("productName"))));
		
		String productName=getText("xpath", property.getProperty("productName"),report);
		swipeToElement(property.getProperty("addToCartBtn"));
		
		
		click("xpath", property.getProperty("addToCartBtn"), report);
		return productName;
		
	}
	
	/*
	 * Description: Method to navigate to Cart
	 * Created By: Anand Mohandas 
	 */
	
	public void navigateToCart(Reporting report) throws Exception
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(property.getProperty("cartIcon"))));
		
		click("id", property.getProperty("cartIcon"), report);
	}
}
