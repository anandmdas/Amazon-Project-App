package test;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.xalan.xsltc.dom.ExtendedSAX;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import pages.CartPage;
import pages.HomePage;
import pages.LanguageAndCountryPage;
import pages.ProductsPage;
import utils.ReadExcel;
import utils.Reporting;
import utils.Utility;

public class SampleTest extends Utility{

	Reporting extentReport;

	/*
	 * Description: Constructor for initializing the class variables and the parent class
	 * Created By: Anand Mohandas 
	 */

	public SampleTest() {
		super();
	}

	/*
	 * Description: Before test method to initialize the drivers and reporting file
	 * Created By: Anand Mohandas 
	 */

	@BeforeTest
	public void testready() 
	{
		SampleTest sample=new SampleTest();

		//Reporting for the Test 

		extentReport=new Reporting();
		extentReport.extentReportInit();
		extentReport.logger=extentReport.report.createTest("Amazon Test");

		//Initialize the driver
		driverInit(extentReport);


	}

	/*
	 * Description: Test method for executing the Amazon App
	 * Created By: Anand Mohandas 
	 */
	
	@Test
	public void amazonAppProductAddition()
	{ 
		ReadExcel readExcel=new ReadExcel();


		readExcel.excelRead("AmazonTest");
		new LanguageAndCountryPage().loginPage(extentReport);
		new HomePage().searchProduct(extentReport, readExcel);
		new HomePage().selectSearchProduct(extentReport);
		String prodName=new ProductsPage().getProductDetails(extentReport);
		new ProductsPage().navigateToCart(extentReport);
		new CartPage().compareProductDetails(extentReport, prodName);
		new CartPage().navigateToCheckout(extentReport);

	}

	/*
	 * Description: After Method to tear down the driver and check execution status
	 * Created By: Anand Mohandas 
	 * Attribute: result - Class object of ITestResult to fetch the overall execution status
	 */

	@AfterMethod
	public void afterMethod(ITestResult result)
	{

		if(ITestResult.FAILURE==result.getStatus())
		{
			extentReport.extentReportFail(result.getThrowable().getMessage());
			//extentReport.logger.fail(result.getThrowable().getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		driver.quit();
		extentReport.report.flush();
	}
}
