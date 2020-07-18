package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class Reporting extends Utility {

	public ExtentHtmlReporter extentHtmlReport;
	public ExtentReports report;
	public ExtentTest logger;


	/*
	 * Description: Reusable function to Capture Screenshot
	 * Created By: Anand Mohandas 
	 */

	public String captureScreen()
	{

		File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String screenShotDestination=null; 

		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(sourceFile);
			byte[] bytes = new byte[(int)sourceFile.length()];
			fileInputStreamReader.read(bytes);       
			screenShotDestination= "/Reports/screenshots/"+System.currentTimeMillis()+".jpeg";
			File destination = new File(screenShotDestination);
			FileUtils.copyFile(sourceFile, destination);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenShotDestination;
	}

	/*
	 * Description: Reusable function to Initialize reporting
	 * Created By: Anand Mohandas 
	 */

	public void extentReportInit()
	{
		extentHtmlReport=new ExtentHtmlReporter(new File(System.getProperty("user.dir") + "/Reports/TestResult.html"));
		report=new ExtentReports();
		report.attachReporter(extentHtmlReport);
	}

	/*
	 * Description: Reusable function to Add passed step in report
	 * Created By: Anand Mohandas 
	 * Attribute; desc- Description to be printed in the report
	 */

	public void extentReportPass(String desc) 
	{
		String tempSS= new Reporting().captureScreen();
		try {
			this.logger.pass(desc, MediaEntityBuilder.createScreenCaptureFromPath(tempSS).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Description: Reusable function to Add Failed step in report
	 * Created By: Anand Mohandas 
	 * Attribute; desc- Description to be printed in the report
	 */

	public void extentReportFail(String desc) 
	{
		String tempSS= new Reporting().captureScreen();
		try {
			this.logger.fail(desc,MediaEntityBuilder.createScreenCaptureFromPath(tempSS).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
