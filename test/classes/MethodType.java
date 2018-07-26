package classes;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import classes.MethodParameters;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import classes.WebDriverClass;
public class MethodType {
	List<WebElement> listOfElements = new ArrayList<WebElement>();
	WebElement element;
	ExecuteTestCases exe = new ExecuteTestCases();
	public void methodExecutor(String methodType, String objectLocators, String actionType, String data) {
		MethodParameters mModel = new MethodParameters();
		mModel.setMethodType(methodType);
		mModel.setObjectLocators(objectLocators);
		mModel.setActionType(actionType);
		mModel.setData(data);
		System.out.println("inside method executor " + methodType + objectLocators + actionType + data );
		//switch case for property type
		switch (methodType.toUpperCase())
		{
		case "ID":
			findElementById(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "NAME":
			findElementByName(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "XPATH":
			findElementByXpath(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "CSS":
			findElementByCssSelector(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
				   	
		default:
			if (actionType.contains(":")) {
			String[] actsplit = actionType.split(":");
			mModel.setActionType(actsplit[1]);
			actionType = actsplit[0];
			System.out.println(actsplit[1]);
			System.out.println(actsplit[0]);
			}
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
			}
			}
	public void findMethod(String methodType, String objectLocators,
		String actionType, String data, MethodParameters model) {
	try {
		Class cl = Class.forName("classes.MethodType");
		MethodType clName = (MethodType) cl.newInstance();
		Method[] methods = cl.getMethods();
		Method methodName = findMethods(actionType, methods);
		methodName.invoke(clName, (Object) model);
		} 
	catch (Exception e) {
		System.out.println("Error to be handled in MethodType Class");
		MainTestNG.LOGGER.info("Error Caught in Test Case:"+exe.getTestName());
		Assert.fail();
		}
		}

//css
	private void findElementByCssSelector(String objectLocators) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(objectLocators)));
		List<WebElement> list1 = WebDriverClass.getInstance().findElements(By.cssSelector(objectLocators));
		listOfElements = list1;
	}

//Id
	public void findElementById(String objectLocators) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		List<WebElement> list1 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(objectLocators)));
		listOfElements = list1;
	}

//xpath
	public void findElementByXpath(String objectLocators) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(objectLocators)));
		List<WebElement> list1 = wait.until(ExpectedConditions.visibilityOfAllElements(WebDriverClass.getDriver().findElements(By.xpath(objectLocators))));
		listOfElements = list1;
	}

//name
	public void findElementByName(String objectLocators) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfAllElements(WebDriverClass.getDriver().findElements(By.name(objectLocators))));
		List<WebElement> list1 = WebDriverClass.getInstance().findElements(By.name(objectLocators));
		listOfElements = list1;
	}
	
//method selector
	public static Method findMethods(String methodName, Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
		if (methodName.equalsIgnoreCase(methods[i].getName().toString())) {
		return methods[i];
		}
		}
		return null;
	}

//click
	public void click(MethodParameters model) 
	{
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(model.getElement().get(0))).click();
	}
	
//submit
	public void submit(MethodParameters model) {
		model.getElement().get(0).submit();
	}


//enter text
	public void enterText(MethodParameters model) {
		System.out.println("model.getElement().get(0)=="+ model.getElement().get(0));
		model.getElement().get(0).sendKeys(model.getData());
	}

//window maximize
	public void maximizeWindow(MethodParameters model) {
		WebDriverClass.getInstance().manage().window().maximize();
	}
	
//timeout	
public void timeout(long i) {
	try {
		Thread.sleep(i);
		}
	catch (InterruptedException e) {
		System.out.println("InvalidFormatException" + e.getMessage());
		}
		}

//frame switching
public void switchToFrame(MethodParameters model) {
	MainTestNG.LOGGER.info("inside switchToFrame");
	WebDriverClass.getInstance().switchTo()
			.frame(model.getElement().get(0));

}

//switch to frame back
public void switchOutOfFrame(MethodParameters model) {
	MainTestNG.LOGGER.info("inside switchOutOfFrame");
	WebDriverClass.getInstance().switchTo().defaultContent();

}

//by visible text
public void clickByVisibleText(MethodParameters model)
{
	WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
	wait.until(ExpectedConditions.elementToBeClickable(model.getElement().get(0)));
	Select sel = new Select(model.getElement().get(0));
	sel.selectByVisibleText(model.getData());
}

//date picker
public void selectDateFromCalendar(MethodParameters model) {
	System.out.println("model.getElement().get(0)=="+ model.getElement().get(0));
	String[] data = model.getData().split("/");
	String dt = data[0]+"-"+data[1]+"-"+data[2];
	model.getElement().get(0).sendKeys(dt);	
}

//upload files
public void upload(MethodParameters model)
{
	String path= model.getData();
	model.getElement().get(0).sendKeys(path);
}
}