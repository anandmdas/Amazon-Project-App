package pages;


import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import utils.Reporting;
import utils.Utility;

public class ProductsPage extends Utility {

	//Element Decleration
	String productName = "//*[@resource-id='title_feature_div']//*[@class='android.view.View']";
	String addToCartBtn = "//*[contains(@resource-id,'add-to-cart-button')]";
	String cartIcon = "com.amazon.mShop.android.shopping:id/action_bar_cart_count";
	
	
	/*
	 * Description: Method to get the product details
	 * Created By: Anand Mohandas 
	 * Attributes: report - class object for generating HTML report and logging
	 */

	public String getProductDetails(Reporting report)
	{
		waitForElementToBeClickable("xpath", productName);
		
		String productNameUi=getText("xpath",productName,report);
		swipeToElement(addToCartBtn);


		click("xpath", addToCartBtn, report);
		return productNameUi;

	}

	/*
	 * Description: Method to navigate to Cart
	 * Created By: Anand Mohandas 
	 * Attributes: report - class object for generating HTML report and logging
	 */

	public void navigateToCart(Reporting report)
	{
		waitForElementToBeClickable("id", cartIcon);

		click("id", cartIcon, report);
	}
}
