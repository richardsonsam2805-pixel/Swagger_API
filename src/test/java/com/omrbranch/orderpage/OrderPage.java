package com.omrbranch.orderpage;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omrbranch.pojo.cancelorder.CancelOrder_Input_Pojo;
import com.omrbranch.pojo.cancelorder.CancelOrder_Output_Pojo;

import com.omrbranch.pojo.getallorders.GetAllOrders_Output_Pojo;
import com.omrbranch.pojo.getallorders.GetAllOrders_Pojo;
import com.omrbranch.postmanbasicauthlogin.Login;
import com.omrbranch.utility.BaseClass;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class OrderPage extends BaseClass{
	String logToken;
	String orderNoText;
	String idText;
	
	private void placeOrder() throws AWTException, InterruptedException {
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https:\\omrbranch.com");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			WebElement txtEmail = driver.findElement(By.id("email"));
			txtEmail.sendKeys("richardsonsam2805@gmail.com");
			WebElement txtPass = driver.findElement(By.id("pass"));
			txtPass.sendKeys("Richie@2805");
			WebElement chckBox = driver.findElement(By.name("remember_me"));
			chckBox.click();
			WebElement btnLogin = driver.findElement(By.xpath("//button[text()='Login']"));
			btnLogin.click();
			WebElement txtSearch = driver.findElement(By.id("search"));
			txtSearch.sendKeys("Nuts");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			WebElement btnAdd = driver.findElement(By.xpath("//a[@class='hover1 font16 fontsemibold colorWhite bgTheme px-4 py-1 radius50 dyna_btn addBtn-18']"));
			btnAdd.click();
			WebElement btnAddVariant = driver.findElement(By.id("cart-24"));
			btnAddVariant.click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement lnkGoToCart = driver.findElement(By.xpath("//a[contains(text(),' Go To Cart ')]"));
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
				    By.id("qty-1824"), "1"
				));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()",lnkGoToCart);
			WebElement btnAddAddress = driver.findElement(By.xpath("//div[@class='diffAddres addAddress d-flex justify-content-center align-items-center mb-md-0 mb-2']"));
			js.executeScript("arguments[0].click()",btnAddAddress);
			WebElement ddnAddType = driver.findElement(By.id("address_type"));
			Select select = new Select(ddnAddType);
			select.selectByIndex(3);
			WebElement txtFirstName = driver.findElement(By.name("first_name"));
			txtFirstName.sendKeys("Sam");
			WebElement txtLastName = driver.findElement(By.name("last_name"));
			txtLastName.sendKeys("Richardson");
			WebElement txtContact = driver.findElement(By.name("mobile"));
			txtContact.sendKeys("7305385996");
			WebElement txtHouseNo = driver.findElement(By.name("apartment"));
			txtHouseNo.sendKeys("28");
			WebElement txtAddress = driver.findElement(By.name("address"));
			txtAddress.sendKeys("84/2, thoraipakkam");
			WebElement ddnStateList = driver.findElement(By.name("state"));
			Select select1 = new Select(ddnStateList);
			select1.selectByValue("35");
			WebElement ddnCityList = driver.findElement(By.name("city"));
			Select select2 = new Select(ddnCityList);
			select2.selectByValue("3659");
			WebElement txtZipcode = driver.findElement(By.name("zipcode"));
			txtZipcode.sendKeys("600037");
			WebElement btnSave = driver.findElement(By.xpath("//button[@class='saveAddress font18 fontSemiBold colorWhite bgTheme radius50 borderNone px-5 py-2 hover1']"));
			js.executeScript("arguments[0].click()",btnSave);
			Thread.sleep(3000);
			WebElement ddnPaymentType = driver.findElement(By.id("payment_type"));
			Select select3 = new Select(ddnPaymentType);
			select3.selectByValue("debit_card");
			WebElement rdoVisaCard = driver.findElement(By.id("visa_card"));
			js.executeScript("arguments[0].click()",rdoVisaCard);
			WebElement txtCardNo = driver.findElement(By.name("card_no"));
			txtCardNo.sendKeys("5555555555552222");
			WebElement ddnCardMonth = driver.findElement(By.id("month"));
			Select select4 = new Select(ddnCardMonth);
			select4.selectByIndex(5);
			WebElement ddnCardYear = driver.findElement(By.id("year"));
			Select select5 = new Select(ddnCardYear);
			select5.selectByIndex(5);
			WebElement txtCardCvv = driver.findElement(By.name("cvv"));
			txtCardCvv.sendKeys("112");
			WebElement btnPlaceOrder = driver.findElement(By.id("placeOrder"));
			btnPlaceOrder.click();
			WebElement txtOrderId = driver.findElement(By.xpath("//p[contains(text(),'Order No: ')]"));
			String text = txtOrderId.getText();
			orderNoText = text.replaceAll("[^0-9]", "");
			System.out.println("Order No:"+orderNoText);
			driver.quit();
		

		
	}
	private void getAllOrders() {
		// 1. Init Rest Assured
		initRestAssured();
		// 2. Add Headers
				List<Header> lstHeader = new ArrayList<Header>();
				Header h1 = new Header("accept", "application/json");
				Header h2 = new Header("Authorization", "Bearer "+Login.logtoken);
				lstHeader.add(h1);
				lstHeader.add(h2);
				Headers headers = new Headers(lstHeader);
				addHeaders(headers);
				// 5. Send Req
				Response response = sendRequest("GET", "https://omrbranch.com/api/getAllOrders");
				GetAllOrders_Output_Pojo getAllOrders_Output_Pojo = response.as(GetAllOrders_Output_Pojo.class);
				ArrayList<GetAllOrders_Pojo> getAllOrders_Output = getAllOrders_Output_Pojo.getData();
//				ArrayList<CreateOrderList_pojo> data = getAllOrders_Output_Pojo.getData();
				for (GetAllOrders_Pojo datum : getAllOrders_Output) {
					String order_no = datum.getOrder_no();
					String orderIdText = String.valueOf(order_no);
					if (orderIdText.equals(orderNoText)) {
						int id = datum.getId();
						idText = String.valueOf(id);
						System.out.println("Order ID:"+idText);
					}
				}
	}
	
	private void CancelOrder() {
		// 1. Init Rest Assured
		initRestAssured();
		// 2. Add Headers
		List<Header> lstHeader = new ArrayList<Header>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer "+Login.logtoken);
		Header h3 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);
		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
		CancelOrder_Input_Pojo cancelOrder_Input_Pojo = new CancelOrder_Input_Pojo(idText);
		addPayload(cancelOrder_Input_Pojo);
		// 5. Send Req
		Response response = sendRequest("POST", "https://omrbranch.com/api/cancelOrder");
		CancelOrder_Output_Pojo cancelOrder_Output_Pojo = response.as(CancelOrder_Output_Pojo.class);
		String message = cancelOrder_Output_Pojo.getMessage();
		System.out.println(message);
	}
	
	public void login() {
		Login login = new Login();
		login.login();
		
	}
	
	public static void main(String[] args) throws AWTException, InterruptedException {
		OrderPage orderPage = new OrderPage();
		orderPage.placeOrder();
		orderPage.login();
		orderPage.getAllOrders();

		orderPage.CancelOrder();
	}

}
