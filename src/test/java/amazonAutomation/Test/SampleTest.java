package amazonAutomation.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.xalan.xsltc.dom.ExtendedSAX;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import amazonAutomation.Model.CartPage;
import amazonAutomation.Model.HomePage;
import amazonAutomation.Model.LanguageAndCountrySelect;
import amazonAutomation.Model.ProductsPage;
import utils.ReadExcel;
import utils.Reporting;
import utils.WrapperTest;

public class SampleTest extends WrapperTest{

	Reporting extentReport;
	public SampleTest() {
		super();
	}
	@BeforeTest
	public void testready() 
	{
		SampleTest sample=new SampleTest();
		//Initialize the driver
		try {
			driverInit();
		
		
		//Reporting for the Test 
		
		extentReport=new Reporting();
		extentReport.extentReportInit();
		extentReport.logger=extentReport.report.createTest("Amazon Test");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void amazonAppProductAddition()
	{ 
		ReadExcel readExcel=new ReadExcel();
		
		try {
			readExcel.excelRead("AmazonTest");
			new LanguageAndCountrySelect().loginPage(extentReport);
			new HomePage().searchProduct(extentReport, readExcel);
			new HomePage().selectSearchProduct(extentReport);
			String prodName=new ProductsPage().getProductDetails(extentReport);
			new ProductsPage().navigateToCart(extentReport);
			new CartPage().compareProductDetails(extentReport, prodName);
			new CartPage().navigateToCheckout(extentReport);
		}
		catch (Exception e) {
			e.printStackTrace();
			extentReport.extentReportFail(e.getMessage());
			
		}
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
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
