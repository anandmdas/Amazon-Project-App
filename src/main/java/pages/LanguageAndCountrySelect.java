package pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporting;
import utils.Utility;

public class LanguageAndCountrySelect extends Utility {

	//Element Decleration
	String amazonLogo = "com.amazon.mShop.android.shopping:id/action_bar_home_logo";
	String skipSignin = "com.amazon.mShop.android.shopping:id/skip_sign_in_button";
	
	
	/*
	 * Description: Method to navigate the Home page
	 * Created By: Anand Mohandas 
	 * Attributes: report - class object for generating HTML report and logging
	 */

	public void loginPage(Reporting report)
	{

		waitForElementToBeClickable("id",amazonLogo);

		click("id", amazonLogo,report);
		waitForElementToBeClickable("id",skipSignin);

		report.extentReportPass("Login Page is displayed");

		click("id", skipSignin,report);
	}
}
