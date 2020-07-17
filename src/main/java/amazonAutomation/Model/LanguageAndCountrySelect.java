package amazonAutomation.Model;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporting;
import utils.WrapperTest;

public class LanguageAndCountrySelect extends WrapperTest {

	Properties property;
	public LanguageAndCountrySelect() {
		// TODO Auto-generated constructor stub
		
		property=new Properties();
		try {
			property=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PageObjectProperty\\languageAndCountry.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Description: Method to navigate the Home page
	 * Created By: Anand Mohandas 
	 */
	
	public void loginPage(Reporting report) throws Exception
	{
		
		WebDriverWait wait=new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(property.getProperty("amazonLogo"))));
		
		click("id", property.getProperty("amazonLogo"),report);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(property.getProperty("skipSignin"))));
		
		report.extentReportPass("Login Page is displayed");
		
		click("id", property.getProperty("skipSignin"),report);
	}
}
