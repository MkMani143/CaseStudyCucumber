package stepDefs;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazeStepDefs {
	static WebDriver driver;
	String verify;
	List<WebElement> cartList;
	String Amount;
	int PriceBefore;
	List<WebElement> ItemsBefore;
	
	@BeforeAll
	public static void Launch() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
//		options.addArguments("-disable notifications");
		DesiredCapabilities cp= new DesiredCapabilities();
		cp.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(cp);
		
		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.demoblaze.com/index.html#");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}
	
	@Given("User is on Home Page")
	public void user_is_on_home_page() {
		driver.findElement(By.id("login2")).click();
	}
	@When("User enters login credentials")
	public void user_enters_login_credentials() {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername"))).sendKeys("Mkmani");
//		driver.findElement(By.id("loginusername")).sendKeys("Mkmani");
		driver.findElement(By.id("loginpassword")).sendKeys("12345");
		driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[3]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
	}
	@Then("Should display Home Page")
	public void should_display_home_page() {
		Assert.assertEquals(driver.findElement(By.id("nameofuser")).getText(), "Welcome Mkmani");
	}
	
	@When("Add Items {string} and {string} to the cart")
	public void add_items_and_to_the_cart(String category, String product) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
	    String strpath="//a[text()='"+category+"']";
	    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(strpath)))).click();
//	    driver.findElement(By.xpath(strpath)).click();
	    strpath="//a[text()='"+product+"']";
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(strpath)))).click();
	    
	    WebElement button=driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']"));
		wait.until(ExpectedConditions.elementToBeClickable(button));
		button.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
//		System.out.println("AlertMessage: "+alert.getText());
		alert.accept();
		verify=product;
	}
	@Then("Item must be added to the cart")
	public void item_must_be_added_to_the_cart() {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.findElement(By.id("cartur")).click();
		List<WebElement> cartList=driver.findElements(By.xpath("//td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(cartList));
		boolean flag=false;
		for(WebElement list:cartList) {
			if(list.getText().equalsIgnoreCase(verify)) {
				Assert.assertEquals(list.getText(), verify);
				flag=true;
			}
			
		}
		Assert.assertTrue(flag);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Home')]"))).click();
	}
	
	@When("Delete an Item to cart")
	public void delete_an_item_to_cart() throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Cart']"))).click();
		List<WebElement> ItemsBefore=driver.findElements(By.xpath("//td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(ItemsBefore));
		String Amount=driver.findElement(By.id("totalp")).getText();
		int PriceBefore=Integer.parseInt(Amount);
		System.out.println(PriceBefore);		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("(//a[text()='Delete'])[1]")))).click();
		Thread.sleep(1000);
	}
	@Then("Item should be deleted in the cart")
	public void item_should_be_deleted_in_the_cart() {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		List<WebElement> ItemsAfter=driver.findElements(By.xpath("//td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(ItemsAfter));
		if(ItemsBefore!=ItemsAfter) {
			Assert.assertTrue(true);
		}
		else {
			Amount=driver.findElement(By.id("totalp")).getText();
			int PriceAfter=Integer.parseInt(Amount);
			System.out.println(PriceAfter);
			Assert.assertNotEquals(PriceBefore, PriceAfter);
		}
	}

	@When("Place Order")
	public void place_order() {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement button=driver.findElement(By.xpath("//button[@class='btn btn-success']"));
		wait.until(ExpectedConditions.elementToBeClickable(button));
		button.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Manikandan");
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("salem");
		driver.findElement(By.xpath("//input[@id='card']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='month']")).sendKeys("5");
		driver.findElement(By.xpath("//input[@id='year']")).sendKeys("20");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))).click();
	}
	@Then("Items should be Purchased")
	public void items_should_be_purchased() throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean msg=driver.findElement(By.xpath("//h2[text()='Thank you for your purchase!']")).isDisplayed();
		Assert.assertTrue(msg);
//		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='OK']"))).click();
		driver.close();
	}
	
	@After
	public void attachImgToReport(Scenario scenario) {
		if(scenario.isFailed()) {
			TakesScreenshot scr=(TakesScreenshot)driver;
				byte[] img=scr.getScreenshotAs(OutputType.BYTES);
				scenario.attach(img, "image/png", "ImageOne");
		}
	}
}
