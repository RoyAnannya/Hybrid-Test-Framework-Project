package classes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class MainTestNG { 
	static Handler filehandler;
	static Formatter formatter = null;
	public static final Logger LOGGER = Logger.getLogger(MainTestNG.class.getName());
	ReadConfigProperty config = new ReadConfigProperty();	
	static String dir = "user.dir";
	public static void main(String[] args) {
	try {
		filehandler = new FileHandler("./log.txt");
		} 
	catch (IOException e) {
		MainTestNG.LOGGER.info(e.getMessage());
		}
		LOGGER.addHandler(filehandler);
		formatter = new SimpleFormatter();
		filehandler.setFormatter(formatter);
		LOGGER.info("Logger Name: " + LOGGER.getName());
		ReadConfigProperty.configpath = System.getProperty(dir);
		MainTestNG test=new MainTestNG();
		test.testng();
		}

	public void testng() {
		TestNG myTestNG = new TestNG();
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName("Softpac Suite");
		mySuite.addListener("classes.TestListener");
		myTestNG.setOutputDirectory("test-output");
		XmlTest myTest = new XmlTest(mySuite);
		myTest.setName("Softpac Test");
		List<XmlClass> myClasses = new ArrayList<XmlClass>();
		myClasses.add(new XmlClass("classes.ExecuteTestCases"));
		myTest.setXmlClasses(myClasses);
		List<XmlTest> myTests = new ArrayList<XmlTest>();
		//multiple tests to be added
		myTests.add(myTest);
		mySuite.setTests(myTests);
		// multiple suites to be added
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();	
		mySuites.add(mySuite);
		myTestNG.setXmlSuites(mySuites);
		myTestNG.setUseDefaultListeners(true);
		myTestNG.run();	
	}
}
