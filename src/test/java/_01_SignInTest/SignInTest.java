package _01_SignInTest;


import common.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class SignInTest {

	@Parameter()
	public String username;
	@Parameter(value = 1)
	public String password;
	@Parameter(value = 2)
	public boolean isBasic;

	private WebDriver driver;

	@Parameterized.Parameters(name = "{index}, isBasic = {2}, username = {0}, password = {1}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][]{
				{"jun28@pingmao.net", "password", true},
				{"", "password", false},
				{"jun29@pingmao.net", "password", false},
				{"jun28@pingmao.net", "", false},
				{"jun28@pingmao.net", "passwd", false}
		});
	}

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("headless"); // Comment this line when debugging
		driver = new ChromeDriver(chromeOptions);
		driver.get(Constants.BASE_URL);
	}

	@Test
	public void SignIn() {
		WebElement goToSignInPage = driver.findElement(By.xpath("//a[@href='/login']"));

		WebDriverWait buttonWait = new WebDriverWait(driver, Constants.TIMEOUT_IN_SECONDS);

		try {
			// Wait until the "login" button is clickable
			buttonWait.until(ExpectedConditions.elementToBeClickable(goToSignInPage));
		} catch (TimeoutException e) {
			Assert.fail("Failed to jump to the login page.");
		}

		goToSignInPage.click();

		WebElement usernameField = driver.findElement(By.xpath("//input[@placeholder='用户名']"));
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='密码']"));

		usernameField.sendKeys(username);
		passwordField.sendKeys(password);

		WebElement signInButton = driver.findElement(By.xpath("//button"));

		/*
		 Wait until the "login" button is clickable
		 TODO: The click action sometimes doesn't complete in case 1.
		*/
		try {
			buttonWait.until(ExpectedConditions.elementToBeClickable(signInButton));
			signInButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Failed to click the SignIn button.");
		}


		if (isBasic) {
			try {
				buttonWait.until(ExpectedConditions.urlToBe(Constants.BASE_URL));
			} catch (TimeoutException e) {
				Assert.fail("Failed to jump to the home page.");
			}

			try {
				WebElement logoutButton = driver.findElement(By.xpath("//div[@class='right menu']"))
						.findElement(By.xpath("//a[@class='item']"));
				buttonWait.until(ExpectedConditions.elementToBeClickable(logoutButton));
				logoutButton.click();
			} catch (TimeoutException e) {
				Assert.fail("Failed to render the index page.");
			}

		} else {
			try {
				buttonWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("negative")));
				WebElement closeIcon = driver.findElement(By.className("close"));
				buttonWait.until(ExpectedConditions.elementToBeClickable(closeIcon));
				closeIcon.click();
			} catch (TimeoutException e) {
				Assert.fail("Error message not shown properly.");
			}
		}


	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
