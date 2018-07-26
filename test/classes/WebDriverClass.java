package classes;
//To create static instance of driver for each class file
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;

public class WebDriverClass {
	static WebDriver driver;
	private WebDriverClass() {
	WebDriverClass.getDriver();
	}
	public static WebDriver getDriver() {
	driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	return driver;
	}
	public static void setDriver(WebDriver driver) {
	WebDriverClass.driver = driver;
	}
	public static WebDriver getInstance() {
	if (driver == null) {
	driver = (WebDriver) new WebDriverClass();
	return driver;
	} 
	else {
	return driver;
	}
	}
}
