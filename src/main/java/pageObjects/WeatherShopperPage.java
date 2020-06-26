package pageObjects;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import commonMethods.CommonFunctions;

public class WeatherShopperPage extends CommonFunctions {
	
	private static By Temperature = By.xpath("//span[@id='temperature']");
	private static By BuyMoisturizers = By.xpath("//button[text()='Buy moisturizers']");
	private static By BuySunScreens = By.xpath("//button[text()='Buy sunscreens']");
	private static By Aloe = By.xpath("//p[contains(text(),'Aloe')]//following-sibling::p");
	private static By Almond = By.xpath("//p[contains(text(),'Almond')]//following-sibling::p");
	private static By SPF50 = By.xpath("//p[contains(text(),'SPF-50')]//following-sibling::p");
	private static By SPF30 = By.xpath("//p[contains(text(),'SPF-30')]//following-sibling::p");
	private static String Add = "//p[contains(text(),'%s')]//following-sibling::p[contains(text(),'%s')]//following-sibling::button[contains(text(),'Add')]";
	private static String ItemsIncart = "//button[text()='Cart - ']/span[text()='%s item(s)']";
	private static By TotalAmount = By.xpath("//p[@id='total']");
	private static By PayWithCard = By.xpath("//span[text()='Pay with Card']");
	private static By Email = By.xpath("//input[@placeholder='Email']");
	private static By CardNumber = By.xpath("//input[@placeholder='Card number']");
	private static By MonthYear = By.xpath("//input[@placeholder='MM / YY']");
	private static By CVC = By.xpath("//input[@placeholder='CVC']");
	private static By ZipCode = By.xpath("//input[@placeholder='ZIP Code']");
	private static String Pay = "//span[contains(text(),'%s')]";
	private static String PaymentSuccessMessage = "//p[text()='%s']";
	private static String CheckOutItems = "//td[text()='%s']";
	private static String ProductName = "//p[contains(text(),'%s')]//following-sibling::p[contains(text(),'%s')]//preceding-sibling::p";
	public static String FirstProductName;
	public static int FirstProductPrice;
	public static String SecondProductName;
	public static int SecondProductPrice;
	public static int TotalPrice;
	
//Getting the text of the product present inside the purchase screen
	public String GetTextOfProduct(String name, int price)
	{
		String NameOfProduct = TextOfElement(By.xpath(String.format(ProductName, name, price)));
		return NameOfProduct;
	}
	
//Verify whether the CheckOut screen contains only the user selected products and respective price
	public void VerifyTheProductsAdded() throws Throwable
	{
		CheckElementDisplayed(By.xpath(String.format(CheckOutItems, FirstProductName)));
		CheckElementDisplayed(By.xpath(String.format(CheckOutItems, FirstProductPrice)));
		CheckElementDisplayed(By.xpath(String.format(CheckOutItems, SecondProductName)));
		CheckElementDisplayed(By.xpath(String.format(CheckOutItems, SecondProductPrice)));		
		String amount = TextOfElement(TotalAmount);
		int ActualAmount = ConvertStringToInteger(amount);
		int ExpectedAmount = FirstProductPrice + SecondProductPrice;
		TotalPrice = ExpectedAmount;
		Assert.assertEquals(ActualAmount, ExpectedAmount);
		HighlightElementJavaScript(TotalAmount);
		UnHighlightElementJavaScript(TotalAmount);
	}
	
//Based on the temperature, selecting two lowest priced Moisturizers or SunScreens
	public void SelectMoisturizersOrSunscreens() throws Throwable
	{
		String temp = TextOfElement(Temperature);
		int CurrentTemperature = ConvertStringToInteger(temp);
//Selecting moisturizers if the temperature is below 19
		if(CurrentTemperature < 19)
		{
			click(BuyMoisturizers);
			int AloeProductCount[] = ConvertStringArrayIntoIntegerArray(Aloe);
			int FirstPrice = ClickTheLowestpriceAmongProducts(AloeProductCount, "Aloe");
			String Firstproduct =  GetTextOfProduct("Aloe", FirstPrice);
			FirstProductName = Firstproduct;
			FirstProductPrice = FirstPrice;
			Thread.sleep(2000);
			CheckItemsInCart(By.xpath(String.format(ItemsIncart, "1")));
			int AlmondProductCount[] = ConvertStringArrayIntoIntegerArray(Almond);
			int SecondPrice = ClickTheLowestpriceAmongProducts(AlmondProductCount, "Almond");
			String Secondproduct =  GetTextOfProduct("Almond", SecondPrice);
			SecondProductName = Secondproduct;
			SecondProductPrice = SecondPrice;
			Thread.sleep(2000);
			CheckItemsInCart(By.xpath(String.format(ItemsIncart, "2")));
		}
//Selecting sunscreens if the temperature is above 34
		else if(CurrentTemperature > 34)
		{
			click(BuySunScreens);
			int SPF50ProductCount[] = ConvertStringArrayIntoIntegerArray(SPF50);
			int FirstPrice = ClickTheLowestpriceAmongProducts(SPF50ProductCount, "SPF-50");
			String Firstproduct =  GetTextOfProduct("SPF-50", FirstPrice);
			FirstProductName = Firstproduct;
			FirstProductPrice = FirstPrice;
			Thread.sleep(2000);
			CheckItemsInCart(By.xpath(String.format(ItemsIncart, "1")));
			int SPF30ProductCount[] = ConvertStringArrayIntoIntegerArray(SPF30);
			int SecondPrice = ClickTheLowestpriceAmongProducts(SPF30ProductCount, "SPF-30");
			String Secondproduct =  GetTextOfProduct("SPF-30", SecondPrice);
			SecondProductName = Secondproduct;
			SecondProductPrice = SecondPrice;
			Thread.sleep(2000);
			CheckItemsInCart(By.xpath(String.format(ItemsIncart, "2")));
		}				
	}
	
//Selecting only the Integer values inside a string
//Converting the datatype from String to Integer
	public int ConvertStringToInteger(String text)
	{
		String priceInString = text.replaceAll("[^0-9]", "");
		int price = Integer.parseInt(priceInString);
		return price;
	}
	
//Converting the String array to Integer array
	public int[] ConvertStringArrayIntoIntegerArray(By Selector)
	{
		List<WebElement> list = getElements(Selector);
		String Product[] = new String[list.size()];
		for(int x=0;x<Product.length;x++)
			{
			    Product[x] = list.get(x).getText().replaceAll("[^0-9]", "");
			}
		int Count[] = new int[list.size()];
		for(int y=0;y<Count.length;y++)
			{
			Count[y]=Integer.parseInt(Product[y]);
			}
		return Count;
	}
	
//Clicking the Lowest price product
	public int ClickTheLowestpriceAmongProducts(int data[], String name) throws Throwable
	{
		Arrays.sort(data);
		click(By.xpath(String.format(Add,name, data[0])));
		return data[0];
	}
	
//Checking whether the required number of items are present inside the cart
	public void CheckItemsInCart(By selector) throws Throwable
	{
		CheckElementDisplayed(selector);
    }
	
//Checking the items available in the cart 
	public void ClickItemsInCart(String data) throws Throwable
	{
		click(By.xpath(String.format(ItemsIncart, data)));
	}
	
//To click on a particular button
	public void ClickButtton(String name) throws Throwable
	{
		if(name.equals("PaywithCard"))
		{
			click(PayWithCard);
		}
		
		else if(name.equals("pay"))
		{
			click(By.xpath(String.format(Pay,TotalPrice)));
			driver.switchTo().defaultContent();
		}
	}
	
//To input Card details
	public void EnterCardDetails(String email,String cardNo,String Monthyear,String cvc,String Zipcode) throws Throwable
	{
		EnterValueIntextBox(Email, email);
		EnterValueIntextBox(CardNumber, cardNo);
		EnterValueIntextBox(MonthYear, Monthyear);
		EnterValueIntextBox(CVC, cvc);
		EnterValueIntextBox(ZipCode, Zipcode);
	}
	
//Checking whether the Payment success message is displayed
	public void CheckForMessage(String message) throws Throwable
	{ 
		CheckElementDisplayed(By.xpath(String.format(PaymentSuccessMessage, message)));
	}	

}
