package classes;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
public class ExecuteTestCases {
static ExcelLibrary lib = new ExcelLibrary();
static WebDriver driver = null;
static ExcelAction act = new ExcelAction();
static protected String mTestCaseName = "";
ReadConfigProperty config = new ReadConfigProperty();	
@BeforeClass
	public void setup() {
	MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()+ "   setup() method called");
		act.readTestSuite();
		act.readTestCaseInExcel();
		act.readTestDataSheet();
		act.readCapturedObjectProperties();
		chrome();
		driver.get(config.getConfigValues("URL"));
		MainTestNG.LOGGER.info("URL launch "+config.getConfigValues("URL"));
		WebDriverClass.setDriver(driver);
		}
	private void chrome() {
		System.setProperty("webdriver.chrome.driver", config.getConfigValues("CHROMEDRIVER"));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		}

@BeforeMethod(alwaysRun = true)
	public void testData(Object[] testData) {
	String testCase = "";
	if (testData != null && testData.length > 0) {
		String testName = null;
		for (Object tstname : testData) {
		if (tstname instanceof String) {
		testName = (String) tstname;
		break;
		}
		}
		testCase = testName;
		}
		this.mTestCaseName = testCase;
		}
	public String getTestName() {
		return this.mTestCaseName;
	}

	public void setTestName(String name) {
		this.mTestCaseName = name;
	}

@Test(dataProvider = "dp")
	public void testSample1(String testName) {
	MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()+ "  @Test method called" + "    " + testName);
	try {
		System.out.println("executing test case" + testName);
		this.setTestName(testName);
		act.testSuiteIterate(testName);
		} 
	catch (Exception ex) {
//		System.out.println("executing test case failed" + testName + " " + ex.getMessage());
		Reporter.log("executing test case failed" + testName + " " + ex.getMessage());
		MainTestNG.LOGGER.info("executing test case failed" + testName + " " + ex.getMessage());
		Assert.fail();
		}
	}

@DataProvider(name = "dp")
	public Object[][] regression() {
		List list = (ArrayList) ExcelAction.listOfTestCases;
		Object[][] data = new Object[list.size()][1];
		MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()+ " TestCases to be executed" + "    " + data);
		for (int i = 0; i < data.length; i++) {
		data[i][0] = (String) list.get(i);
		}
		return data;
	}
@AfterSuite
public void closeBrowser()
{
	driver.quit();
}

}
