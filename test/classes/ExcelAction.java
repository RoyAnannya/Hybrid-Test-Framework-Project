package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.CapturedObjectPropModel;
import classes.TestCase;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import classes.MethodType;

public class ExcelAction {
	WebDriver driver;
	static ExcelLibrary excel = new ExcelLibrary();
	static ReadConfigProperty config = new ReadConfigProperty();
	static Map<String, Object> testCaseSheet = new HashMap<String, Object>();
	static Map<String, String> readFromConfigFile = new HashMap<String, String>();
	static Map<String, Object> testSuiteSheet = new HashMap<String, Object>();
	static Map<String, Object> testDataSheet = new HashMap<String, Object>();
	static Map<String, Object> capObjPropSheet = new HashMap<String, Object>();
	static List listOfTestCases = new ArrayList();
	int numberOfTimeExecution = 0;
	MethodType methodtype = new MethodType();
	String testcasepth = "TestCasePath";
	
	public static void main(String[] args) 
	{
		ExcelAction action = new ExcelAction();
		action.readCapturedObjectProperties();
	}

	public void readTestDataSheet() 
	{
		String sheetName;
		String pathOFFile = config.getConfigValues(testcasepth);
		List<String> list = ExcelLibrary.getNumberOfSheetsinTestDataSheet(config.getConfigValues(testcasepth));
		for (int i = 0; i < list.size(); i++) 
		{
			sheetName = list.get(i);
			Map<String, Object> temp1 = new HashMap<String, Object>();
		try 
		{
			Reporter.log("sheetName" + sheetName + "----"+ "sheetName, pathOFFile" + pathOFFile);
			List listColumnNames = ExcelLibrary.getColumnNames(sheetName,pathOFFile,ExcelLibrary.getColumns(sheetName, pathOFFile));
			// iterate through columns in sheet
			for (int j = 0; j < listColumnNames.size(); j++) 
			{// get Last Row for each Column
				int row = 1;
				List listColumnValues = new ArrayList();
			do {
				listColumnValues.add(ExcelLibrary.readCell(row, j,sheetName, pathOFFile));
				row++;
				} 
			while ((ExcelLibrary.readCell(row, j, sheetName,pathOFFile)) != null);
				temp1.put((String) listColumnNames.get(j), listColumnValues);
				}
				listColumnNames.clear();
				} 
			catch (InvalidFormatException | IOException e) {
				e.printStackTrace();
				}
				testDataSheet.put(sheetName, temp1);
			}
		}

	public void testSuiteIterate(String tcName) {
		String key = tcName;
		TestCase temp = (TestCase) testCaseSheet.get(key);
		List testStepId = temp.getTestStepId();
		Reporter.log("size====" + testStepId.size());
		System.out.println("size====" + testStepId.size());
		List dataColValues = null;
		int noOfExecution = 0;
		for (int i = 0; i < testStepId.size(); i++)
		{
//		System.out.println("inside for loop " + temp.getTestData().get(i));
		if (!(temp.getTestData().get(i).isEmpty())) 
		{
			if (temp.getTestData().get(i).contains(".")) 
			{
				String data = temp.getTestData().get(i);
				String[] testDataArray = data.split("\\.");
				dataColValues = getColumnValue(testDataArray);
				noOfExecution = dataColValues.size();
//				System.out.println("has a . execution times ="+ noOfExecution);
				break;
				}
			} 
		else{
				noOfExecution = 0;
			}
		}
		MainTestNG.LOGGER.info("columnValue addedd newly numberOfTimesExecution==="+ dataColValues);
		MainTestNG.LOGGER.info("testCaseExecution==" + noOfExecution);
		if (noOfExecution != 0) {
//			System.out.println("number of execution is not 0" + noOfExecution);
			for (int execution = 0; execution < noOfExecution; execution++) {
			for (int i = 0; i < testStepId.size(); i++) {
				String methodType = temp.getMethodType().get(i);
				System.out.println("method type" + methodType);
				String objectLocators = temp.getObjectNameFromPropertiesFile().get(i);
				System.out.println("objectLocators type" + objectLocators);
				String actionType = temp.getActionType().get(i);
				// Data Sheet logic
				if (!(temp.getTestData().get(i).isEmpty())) {
					System.out.println("inside if=======");
					if (temp.getTestData().get(i).contains(".")) {
					String data = temp.getTestData().get(i);
					String[] testDataArray = data.split("\\.");
					List columnValue = getColumnValue(testDataArray);
					Reporter.log("column value======" + columnValue);
					MainTestNG.LOGGER.info("column value======" + columnValue);
					Reporter.log("column value size==========="+ columnValue.size());
					System.out.println("column value size==========="+ columnValue.size());
				try {
					Reporter.log("testCaseExecution======================"+ noOfExecution);
					System.out.println("testCaseExecution======================"+ noOfExecution);
					List<String> list = readLocators(methodType,objectLocators);
					MainTestNG.LOGGER.info("methodType="+ methodType);
					MainTestNG.LOGGER.info("objectLocators as name="+ objectLocators);
					methodType = list.get(0);objectLocators = list.get(1);
					methodtype.methodExecutor(methodType,objectLocators, actionType, columnValue.get(execution).toString());
					}
				catch (IndexOutOfBoundsException e) {
					String s = e.getMessage();
					System.out.println("exception ="+ e.getMessage());
					MainTestNG.LOGGER.info("exception ="+ e.getMessage());
					throw new IndexOutOfBoundsException("data column is blank..Please enter value in datasheet"+ s);
					} 
				catch (Exception e) {
					System.out.println("exception ="+ e.getMessage());
					}
				}
				if (execution == noOfExecution) {
				break;
					}
				} 
				else {
//					System.out.println("inside else");
					driver = WebDriverClass.getInstance();
//					System.out.println("driver");
					List<String> list = readLocators(methodType,objectLocators);
					System.out.println("list size" + list.size());
					if (list.size() >= 1) {
						methodType = list.get(0);
						objectLocators = list.get(1);
						} 
					else{
						// list size is null
//						System.out.println("size is null");
						}
//					System.out.println("before method executor");
					methodtype.methodExecutor(methodType, objectLocators,actionType, null);
					}
				}
				if (execution == noOfExecution) {
					break;
				}
				}
				}
				else {
				for (int i = 0; i < testStepId.size(); i++) {
				String methodType = temp.getMethodType().get(i);
				String objectLocators = temp.getObjectNameFromPropertiesFile().get(i);
				String actionType = temp.getActionType().get(i);
				driver = WebDriverClass.getInstance();
				List<String> list = readLocators(methodType, objectLocators);
				methodType = list.get(0);
				objectLocators = list.get(1);
				MainTestNG.LOGGER.info("methodType=" + methodType);
				MainTestNG.LOGGER.info("objectLocators=" + objectLocators);
				methodtype.methodExecutor(methodType, objectLocators,actionType, null);
			}
		}
	}
	
	private List getColumnValue(String[] testDataArray) {
		Map<String, Object> dataSheet = (HashMap<String, Object>) testDataSheet.get(testDataArray[0]);
		List coulmnValue = (ArrayList) dataSheet.get(testDataArray[1]);
		MainTestNG.LOGGER.info("Column Value used : "+coulmnValue);
		return coulmnValue;
	}


	public void readTestSuite() {
		readFromConfigFile = config.readConfigFile();
		for (String suiteName : readFromConfigFile.values()) {
			String testSuiteFilePath = config.getConfigValues("TestSuiteName");
			System.out.println(testSuiteFilePath);
			List<String> suiteSheets = ExcelLibrary.getNumberOfSheetsinSuite(testSuiteFilePath);
			System.out.println(suiteSheets.size());
			for (int i = 0; i < suiteSheets.size(); i++) {
				String sheetName = suiteSheets.get(i);
				System.out.println(sheetName);
			if (suiteName.trim().equalsIgnoreCase(sheetName)) {
				Map<String, Object> temp1 = new HashMap<String, Object>();
			try {
				for (int row = 1; row <= ExcelLibrary.getRows(sheetName, testSuiteFilePath); row++) 
				{
				String testCaseName = ExcelLibrary.readCell(row, 0,suiteName.trim(), testSuiteFilePath);
				String testCaseState = ExcelLibrary.readCell(row,1, suiteName.trim(), testSuiteFilePath);
				System.out.println("testCaseName = " + testCaseName+" "+ testCaseState);
			if (("YES").equalsIgnoreCase(testCaseState)) {
				listOfTestCases.add(testCaseName);
				}
				temp1.put(testCaseName, testCaseState);
				}
//				System.out.println("testCaseName outside the for loop");
				Reporter.log("listOfTestCases=============*****************"+ listOfTestCases);
				testSuiteSheet.put(suiteName, temp1);
				} 
			catch (InvalidFormatException | IOException e) {
				}
				}
				}
		}
	}

	public void readTestCaseInExcel() {
		String testsheetnme = "TestCase_SheetName";
		String testCasePath = config.getConfigValues(testcasepth);
		String testCaseSheetName = config.getConfigValues(testsheetnme);
		TestCase tc = null;
		try {
		for (int row = 1; row <= ExcelLibrary.getRows(testCaseSheetName,testCasePath); row++) 
		{
		if (!(ExcelLibrary.readCell(row, 0, testCaseSheetName,testCasePath).isEmpty())) {
			tc = new TestCase();
			tc.setTestCaseName(ExcelLibrary.readCell(row, 0,testCaseSheetName, testCasePath));
			tc.setTestStepId(ExcelLibrary.readCell(row, 1,testCaseSheetName, testCasePath));
			tc.setMethodType(ExcelLibrary.readCell(row, 3,testCaseSheetName, testCasePath));
			tc.setObjectNameFromPropertiesFile(ExcelLibrary.readCell(row, 4, testCaseSheetName, testCasePath));
			tc.setActionType(ExcelLibrary.readCell(row, 5,testCaseSheetName, testCasePath));
			tc.setOnFail(ExcelLibrary.readCell(row, 6,testCaseSheetName, testCasePath));
			tc.setTestData(ExcelLibrary.readCell(row, 7,testCaseSheetName, testCasePath));
			testCaseSheet.put(ExcelLibrary.readCell(row, 0,testCaseSheetName, testCasePath), tc);
		}
		else {
			tc.setTestStepId(ExcelLibrary.readCell(row, 1,testCaseSheetName, testCasePath));
			tc.setMethodType(ExcelLibrary.readCell(row, 3,testCaseSheetName, testCasePath));
			tc.setObjectNameFromPropertiesFile(ExcelLibrary.readCell(row, 4, testCaseSheetName, testCasePath));
			tc.setActionType(ExcelLibrary.readCell(row, 5,testCaseSheetName, testCasePath));
			tc.setOnFail(ExcelLibrary.readCell(row, 6,testCaseSheetName, testCasePath));
			tc.setTestData(ExcelLibrary.readCell(row, 7,testCaseSheetName, testCasePath));
				}
			}
			} 
		catch (InvalidFormatException e) {
			System.out.println();
			} 
		catch (IOException e) {
			System.out.println();
		}
	}

	public void clean() {
		excel.clean();
	}

	public void readCapturedObjectProperties() {
		String testSheetName = "CapturedObjectProperties";
		String testCasePath = config.getConfigValues(testcasepth);
		MainTestNG.LOGGER.info("testCasePath==" + testCasePath);
		try {
			int totrows = ExcelLibrary.getRows(testSheetName, testCasePath);
			MainTestNG.LOGGER.info("total rows=" + totrows);
			String prevPagename = "";
			Map<String, Object> pageInfo = null;
		for (int j = 1; j <= totrows; j++) {
			String pagename = ExcelLibrary.readCell(j, 0, testSheetName,testCasePath);
		if (prevPagename.equals(pagename)) {
			String page = ExcelLibrary.readCell(j, 0, testSheetName,testCasePath);
			String name = ExcelLibrary.readCell(j, 1, testSheetName,testCasePath);
			String property = ExcelLibrary.readCell(j, 2,testSheetName, testCasePath);
			String value = ExcelLibrary.readCell(j, 3, testSheetName,testCasePath);
			CapturedObjectPropModel capModel = new CapturedObjectPropModel();
			capModel.setPage(page);
			capModel.setName(name);
			capModel.setProperty(property);
			capModel.setValue(value);
			pageInfo.put(name, capModel);
			} 
		else {
		if (prevPagename != null) {
			capObjPropSheet.put(prevPagename, pageInfo);
			}
			pageInfo = new HashMap<String, Object>();
			String page = ExcelLibrary.readCell(j, 0, testSheetName,testCasePath);
			String name = ExcelLibrary.readCell(j, 1, testSheetName,testCasePath);
			String property = ExcelLibrary.readCell(j, 2,testSheetName, testCasePath);
			String value = ExcelLibrary.readCell(j, 3, testSheetName,testCasePath);
			CapturedObjectPropModel capModel = new CapturedObjectPropModel();
			capModel.setPage(pagename);
			capModel.setName(name);
			capModel.setProperty(property);
			capModel.setValue(value);
			pageInfo.put(name, capModel);
			prevPagename = pagename;
			}
		if (prevPagename != null) {
			capObjPropSheet.put(prevPagename, pageInfo);
			}
			}
			} 
		catch (InvalidFormatException e) {
			e.printStackTrace();
			} 
		catch (IOException e) {
			e.printStackTrace();
			}
		}

	public List<String> readLocators(String page, String name) {
		Map<String, Object> temp = (Map<String, Object>) capObjPropSheet.get(page);
		List<String> locators = new ArrayList<>();
//		System.out.println("inside readLocators " + page + name);
		// System.out.println("inside readLocators ");
		if (capObjPropSheet.get(page) != null) {
//			System.out.println("inside if ");
			CapturedObjectPropModel c = (CapturedObjectPropModel) temp.get(name);
//			System.out.println("aftr CapturedObjectPropModel ");
		try {
		if (c != null) {
		// System.out.println(c.getPage());
		// System.out.println(c.getName());
		if (c.getPage().equals(page) && c.getName().equals(name)) {
		// System.out.println("inside 222222 if ");
			locators.add(c.getProperty());
			locators.add(c.getValue());
			MainTestNG.LOGGER.info("locators" + locators);
		}
		else {
		// System.out.println("inside 222222 else ");
		}
		} 
		else {
		//System.out.println("c is nullllll ");
			}
			} 
		catch (Exception e) {
			System.out.println("exception=" + e.getMessage());
			}
			} 
		else {
//			System.out.println("inside else ");
			}
		MainTestNG.LOGGER.info("size" + locators.size());
		return locators;
	}
}
