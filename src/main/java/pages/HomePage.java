package pages;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ReadExcel;
import utils.Reporting;
import utils.Utility;

public class HomePage extends Utility {

	

	//Element declaration
	
	String searchBox = "com.amazon.mShop.android.shopping:id/rs_search_src_text";
	String productCount = "com.amazon.mShop.android.shopping:id/rs_results_count_text";
	String productList = "//*[@resource-id='com.amazon.mShop.android.shopping:id/list_product_linear_layout']";
	String searchProdList="//*[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions']";
	
	

	/*
	 * Description: Method to Search a Product
	 * Created By: Anand Mohandas 
	 * Attributes: report - class object for generating HTML report and logging
	 * 			   readExclel - class object for getting the data for the particular testcase
	 */

	public void searchProduct(Reporting report, ReadExcel readExcel)
	{
		waitForElementToBeClickable("id", searchBox);

		click("id", searchBox, report);

		waitForElementToBeClickable("id", searchBox);

		inputValue("id", searchBox, report, readExcel.getValue("SearchText"));

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		selectRandomValue("xpath", searchProdList, report);

	}

	/*
	 * Description: Method to select a searched product
	 * Created By: Anand Mohandas 
	 * Attributes: report - class object for generating HTML report and logging
	 */

	public void selectSearchProduct(Reporting report)
	{

		waitForElementToBeClickable("id",productCount);

		selectRandomValue("xpath",productList, report);
	}
}
