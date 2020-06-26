package definitions;

import io.cucumber.java.en.And;

import pageObjects.WeatherShopperPage;

public class WeatherShopperdefinitions extends WeatherShopperPage {

	@And("^Enter the Email(.*) , Card number(.*) , Month and year(.*) , CVC(.*) , Zipcode(.*)$")
    public void enterCarddetails(String email,String Cardno,String mmyy,String cvc,String zip) throws Throwable
    {
		EnterCardDetails(email,Cardno,mmyy,cvc,zip);
    }
	
	@And("^Open the browser and navigate to Weather Shopper URL$")
    public void OpenBrowserandloadURl() throws Throwable
    {
		launchDriver();
		homepage();
    }
	
	@And("^User is in (.*) screen$")
    public void OpenBrowserandloadURl(String LandingPageTitle) throws Throwable
    {
		landingpage(LandingPageTitle);
    }
	 
	@And("^Based on the temperature select Moisturizers or Sunscreens and add two products to cart$")
    public void ClickAndAddSunscreensMoisturizers() throws Throwable
    {
		SelectMoisturizersOrSunscreens();
    }
	
	@And("^Add products(.*) and click on the Items in cart$")
    public void ClickOnItemsIncart(String ProductCount) throws Throwable
    {
		ClickItemsInCart(ProductCount);
    }
	
	@And("^Check the products present in Checkout screen$")
    public void CheckOutProducts() throws Throwable
    {
		VerifyTheProductsAdded();
    }
	
	@And("^Click the (.*) button$")
    public void buttonClick(String ButtonName) throws Throwable
    {
		ClickButtton(ButtonName);
    }
	
	@And("^The User is in the (.*) screen$")
    public void InscreenTitle(String InscreenPageTitle) throws Throwable
    { 
		InsideScreenTitle(InscreenPageTitle);
    }
	
	@And("^Check the Message(.*) is displayed$")
    public void VerifytheMessage(String message) throws Throwable
    {
		CheckForMessage(message);
    }
	
	@And("^Close the browser$")
    public void BrowserClose() throws Throwable
    {
		tearDown();
    }	
	
}
