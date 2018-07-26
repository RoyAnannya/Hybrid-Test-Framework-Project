# SoftPac

How to build:
-------------
Unzip file
Import in eclipse existing files
change the directories in properties file
Run the Execute Test Cases file as TestNg test
For Error Reports please check test-output/emailable-report.html


How to use:
-----------
1. The TestSuite.xslx is to specify the testcases names and execution status with either YES or NO. 
 
2. The TestCase.xslx in which first sheet is "TestCase" will have columns which describe each test cases with details as
test case name, TestStepId, TestSteps unique step ids like TC1,TC2 etc, ObjectLocators, ActionType is the operation, Data 

3.Next Sheet is the "CapturedObjectProperties" sheet where we define the properties of the webelement. The sheet has the columns with details as 
	i.   First coloumn is "Page" 
	ii.  Second one is the "NAME" 
	iii. Third coloumn is "Property" like ID, XPATH,CSS and NAME
	iv.  Fourth one is "Value" which is corresponding to the property. 
	
4.In config file change the location of all the dependencies.

Dependencies:
-------------

1.Poi framework jars
	i.dom4j
	ii.poi-3.9
	iii.poi-ooxml-3.9
	iv.poi-ooxml-schemas-3.9
	v.xmlbeans-2.6.0

2.TstNG jars
	i.testng-6.9.10

3.Selenium standalone server jars
	i.selenium-server-standalone-2.53.1

4.Chromedriver.exe

5. Loging jar:
	i.apache-logging-log4j
