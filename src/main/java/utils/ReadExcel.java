package utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ReadExcel extends Utility {

	HashMap<String, String> excelValue;
	Workbook wb;
	FileInputStream fs;

	/*
	 * Description: Reusable function to Open an excel file
	 * Created By: Anand Mohandas 
	 */

	public void openExcel()  {
		try
		{
			String testData = System.getProperty("user.dir");
			Properties prop=new Properties();
			prop=loadPropertyFile(System.getProperty("user.dir")+"\\src\\main\\resources\\PropertyFiles\\config.properties");
			fs = new FileInputStream(testData + prop.getProperty("TestdataPath"));
			wb = new XSSFWorkbook(fs);
		}catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/*
	 * Description: Reusable function to read the testdata for a testcase
	 * Created By: Anand Mohandas 
	 * Attribute: testCaseName - Name of the test case which needs to be read from excel file
	 */
	public void excelRead(String testCaseName) {
		openExcel();
		String sheetName = "generic";
		String testSheet = "";
		Sheet sprintSheet;
		Sheet sh = wb.getSheet(sheetName);
		excelValue = new HashMap();

		int lastRow = sh.getLastRowNum();
		int rowNum;
		Row row = null;

		for (rowNum = 1; rowNum <= lastRow; rowNum++) {
			row = sh.getRow(rowNum);
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				testSheet = row.getCell(1).getStringCellValue();
				break;
			}

		}
		sprintSheet = wb.getSheet(testSheet);
		Row row0 = sprintSheet.getRow(0);
		lastRow = sprintSheet.getLastRowNum();
		for (rowNum = 1; rowNum <= lastRow; rowNum++) {
			row = sprintSheet.getRow(rowNum);
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				break;
			}
		}
		for (int i = 1; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getCellType() == 1)
				excelValue.put(row0.getCell(i).getStringCellValue(), row.getCell(i).getStringCellValue());
			else if (row.getCell(i).getCellType() == 0)
				excelValue.put(row0.getCell(i).getStringCellValue(), row.getCell(i).getNumericCellValue() + "");
			else if (row.getCell(i).getCellType() == 3)
				continue;
		}
	}

	/*
	 * Description: Reusable function to fetch the specific testdata
	 * Created By: Anand Mohandas 
	 * Attribute: key - column name in the testcase so the corresponding cell value is returned
	 */
	public String getValue(String key) {
		return excelValue.get(key);
	}
	/*
	 * Description: Reusable function to close excel
	 * Created By: Anand Mohandas 
	 */
	public void closeExcel() throws IOException {
		fs.close();
	}
}
