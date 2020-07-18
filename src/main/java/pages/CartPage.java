package pages;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporting;
import utils.Utility;

public class CartPage extends Utility{

	
	//Element Decleration
	
	String productName="(//*[@resource-id='activeCartViewForm']//*[@class='android.widget.TextView'])[1]";
	String productCount = "(//*[@resource-id='activeCartViewForm']//*[@class='android.widget.TextView'])[2]";
	String checkOutBtn = "(//*[@resource-id='sc-mini-buy-box']//*[@class='android.widget.Button'])";
	
	
	/*
	 * Description: Method to compare the products in cart
	 * Created By: Anand Mohandas 
	 * Atttributes: report - class object for generating HTML report and logging
	 * 				prodName - Product Name retrieved from product details page for comparison 
	 */

	public void compareProductDetails(Reporting report,String prodName)
	{

		waitForElementToBeClickable("xpath", productName);

		String uiProdName=getText("xpath",productName, report);
		System.out.println("uiProdName"+uiProdName);
		StringTokenizer st=new StringTokenizer(uiProdName, "...");

		System.out.println("prodName"+prodName);
		System.out.println("uiProdName"+uiProdName);
		stringContains(st.nextToken(),prodName, report);
		String elemCount=getText("xpath", productCount, report);
		stringContains(elemCount, "1", report);

	}

	/*
	 * Description: Method to navigate to checkout page
	 * Created By: Anand Mohandas 
	 * Atttributes: report - class object for generating HTML report and logging
	 */

	public void navigateToCheckout(Reporting report)
	{
		click("xpath",checkOutBtn, report);
	}
}
