package commonMethods;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonFunctions {

	public String WorkingDirectory = System.getProperty("user.dir");
	public static WebDriver driver;

	private static String PAGETITLE = "//h2[text()='%s']";
	private static String InScreenTitle = "//h1[text()='%s']";

//Launching the Browser 
	@SuppressWarnings("deprecation")
	public void launchDriver() throws Throwable {
		if (readProperty("BrowserName").equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (readProperty("BrowserName").equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (readProperty("BrowserName").equals("InternetExplorer")) {
			System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (readProperty("BrowserName").equals("Safari")) {
			driver = new SafariDriver();
		} else if (readProperty("BrowserName").equals("Edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Windows\\System32\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(readProperty("ApplicationURL"));
//		driver.get(System.getProperty("ApplicationURL"));
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

//Reading the data from the Properties file
	public String readProperty(String val) throws FileNotFoundException {
		Properties Data;
		Data = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(WorkingDirectory + "\\src\\main\\java\\commonMethods\\Data.properties");
			Data.load(fis);

		} catch (IOException IO) {
			IO.printStackTrace();
		}
		return Data.getProperty(val);

	}

//Check whether user is in the correct homepage
	public void homepage() throws InterruptedException, ClassNotFoundException, Throwable {
		String currentURL = driver.getCurrentUrl();
		try {
			if (currentURL.equalsIgnoreCase(readProperty("ApplicationURL"))) {
				System.out.println("User is in Weather Shopper URL");
			} else {
				driver.get(readProperty("ApplicationURL"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

//Check whether the User is navigated to correct page
	public void landingpage(String ExpectedScreentitle) throws Throwable {
		Thread.sleep(1500);
		WaitFortheParticularElement(By.xpath(String.format(PAGETITLE, ExpectedScreentitle)));
		String Titleinscreen = TextOfElement(By.xpath(String.format(PAGETITLE, ExpectedScreentitle)));
		Assert.assertEquals(Titleinscreen, ExpectedScreentitle);
	}

//Switch to a frame and check whether the User is navigated to correct page
	public void InsideScreenTitle(String ExpectedScreentitle) throws Throwable {
		SwitchFrame(0);
		WaitFortheParticularElement(By.xpath(String.format(InScreenTitle, ExpectedScreentitle)));
		String Titleinscreen = TextOfElement(By.xpath(String.format(InScreenTitle, ExpectedScreentitle)));
		Assert.assertEquals(Titleinscreen, ExpectedScreentitle);
	}

//Finding a single WebElement inside a webpage
	public WebElement getElement(By Selector) {
		WaitFortheParticularElement(Selector);
		WebElement Element = driver.findElement(Selector);
		return Element;
	}

//Finding multiple WebElements inside a webpage
	public List<WebElement> getElements(By Selector) {
		List<WebElement> Element = driver.findElements(Selector);
		return Element;
	}

//Get the text of particular WebElement
	public String TextOfElement(By Selector) {
		WaitFortheParticularElement(Selector);
		WebElement Element = getElement(Selector);
		String Data = Element.getText();
		return Data;
	}

//Switching between frames
	public void SwitchFrame(int Frameindex) {
		driver.switchTo().frame(Frameindex);
	}

//Clicking the WebElement
	public void click(By Selector) throws Throwable {
		WaitFortheParticularElement(Selector);
		HighlightElementJavaScript(Selector);
		UnHighlightElementJavaScript(Selector);
		JavascriptClick(Selector);
	}

//Entering the data inside a textbox
	public void EnterValueIntextBox(By Selector, String data) throws Throwable {
		WaitFortheParticularElement(Selector);
		WebElement Element = getElement(Selector);
		Element.sendKeys(data);
		Thread.sleep(2000);
	}

//Explicit Wait
	public void WaitFortheParticularElement(By Selector) {
		WebDriverWait w = new WebDriverWait(driver, 10);
		w.until(ExpectedConditions.visibilityOfElementLocated(Selector));
	}

//Highlighting a WebElement
	public void HighlightElementJavaScript(By selector) throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = getElement(selector);
		js.executeScript("arguments[0].style.border='3px solid red'", ele);
		Thread.sleep(1000);
	}

//UnHighlighting a WebElement
	public void UnHighlightElementJavaScript(By selector) throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = getElement(selector);
		js.executeScript("arguments[0].style.border='0px solid red'", ele);
		Thread.sleep(500);
	}

//Clicking an Element using JavaScriptExeceutor
	public void JavascriptClick(By selector) throws Throwable {
		WebElement ele = getElement(selector);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", ele);
		Thread.sleep(500);
	}

//Checking whether a WebElement is displayed on the WebPage
	public void CheckElementDisplayed(By selector) throws Throwable {
		boolean Element = getElement(selector).isDisplayed();
		if (Element == true) {
			HighlightElementJavaScript(selector);
			UnHighlightElementJavaScript(selector);
		} else if (Element == false) {
			System.out.println("The following element is not displayed : " + selector);
			Assert.assertTrue(Element);
		}
	}

//Close the browser    
	public void tearDown() {
		driver.close();
	}

}
