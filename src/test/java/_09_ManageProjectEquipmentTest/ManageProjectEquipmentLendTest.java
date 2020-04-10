package _09_ManageProjectEquipmentTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Signature;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static common.Constants.*;


@RunWith(Parameterized.class)
public class ManageProjectEquipmentLendTest {

	@Parameter()
	public boolean isBasic;
	@Parameter(value = 1)
	public String propertyID;
	@Parameter(value = 2)
	public String logTime;
	@Parameter(value = 3)
	public String returnTime;


	@SuppressWarnings("FieldCanBeLocal")
	private final String projectManagerUsername = "jun28@pingmao.net";
	@SuppressWarnings("FieldCanBeLocal")
	private final String configurationManagerUsername = "mpan@hotmail.com";
	@SuppressWarnings("FieldCanBeLocal")
	private final String password = "password";
	@SuppressWarnings("FieldCanBeLocal")
	private final String projectID = "2020-4577-D-01";

	@Parameterized.Parameters(name = "{index}, isBasic = {0}, propertyID = {1}, logTime = {2}, returnTime = {3}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][]{
				{false, "", "2020-04-02 12:00:00", "2020-05-01 12:00:00"},
				{false, "290102211596255233", "", "2020-05-01 12:00:00"},
				{false, "290102211596255233", "2020-04-02 12:00:00", ""},
				{false, "290102211596255233", "2020-04-02 12:00:00", "2020-04-01 12:00:00"},
				{true, "290102211596255233", "2020-04-02 12:00:00", "2020-05-01 12:00:00"},
		});
	}

	private WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("headless"); // Comment this line when debugging
		driver = new ChromeDriver(chromeOptions);
		driver.get(BASE_URL);
	}

	void SignIn(@SuppressWarnings("SameParameterValue") String username, @SuppressWarnings("SameParameterValue") String password) {
		WebDriverWait buttonWait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		try {
			buttonWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/login']")));
		} catch (TimeoutException e) {
			Assert.fail("Login button not found.");
		}
		WebElement goToSignInPage = driver.findElement(By.xpath("//a[@href='/login']"));


		// Wait until the "login" button is clickable
		buttonWait.until(ExpectedConditions.elementToBeClickable(goToSignInPage));

		goToSignInPage.click();

		WebElement usernameField = driver.findElement(By.xpath("//input[@placeholder='用户名']"));
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='密码']"));

		usernameField.sendKeys(username);
		passwordField.sendKeys(password);

		WebElement signInButton = driver.findElement(By.xpath("//button"));

		buttonWait.until(ExpectedConditions.elementToBeClickable(signInButton));
		signInButton.click();

		buttonWait.until(ExpectedConditions.urlToBe(BASE_URL));
	}

	void LogOut() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		try {
			WebElement logoutButton = driver.findElement(By.xpath("//a[text() = '登出']"));
			wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
			logoutButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Failed to logout.");
		}
	}

	@Test
	public void ManageProjectEquipmentLend() throws Exception {
		SignIn(projectManagerUsername, password);
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[4]/a")));
		} catch (TimeoutException e) {
			Assert.fail("Project row not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[4]/a"));
		projectDetailButton.click();

		WebElement projectDeviceTab = driver.findElement(By.xpath("//a[@href='/projectDevice']"));
		projectDeviceTab.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDevice"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project device page.");
		}

		WebElement logNewDeviceButton = driver.findElement(By.xpath("//button[text() = '登记新设备']"));
		logNewDeviceButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = '登记设备']")));
		} catch (TimeoutException e) {
			Assert.fail("Log device form not popped.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择登记的设备ID')]")));
		} catch (TimeoutException e) {
			Assert.fail("Device ID field not found.");
		}

		if (propertyID.length() != 0) {
			WebElement deviceIDField = driver.findElement(By.xpath("//div/div[contains(text(), '选择登记的设备ID')]/../input"));

			deviceIDField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			deviceIDField.sendKeys(Keys.DELETE);
			deviceIDField.sendKeys(propertyID);
			deviceIDField.sendKeys(Keys.ENTER);
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='登记时间：格式为yyyy-MM-dd HH:mm:ss']")));
		} catch (TimeoutException e) {
			Assert.fail("Log time field not found.");
		}
		WebElement logTimeField = driver.findElement(By.xpath("//input[@placeholder='登记时间：格式为yyyy-MM-dd HH:mm:ss']"));

		logTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logTimeField.sendKeys(Keys.DELETE);
		logTimeField.sendKeys(logTime);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='归还时间：格式为yyyy-MM-dd HH:mm:ss']")));
		} catch (TimeoutException e) {
			Assert.fail("Return time field not found.");
		}
		WebElement returnTimeField = driver.findElement(By.xpath("//input[@placeholder='归还时间：格式为yyyy-MM-dd HH:mm:ss']"));

		returnTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		returnTimeField.sendKeys(Keys.DELETE);
		returnTimeField.sendKeys(returnTime);

		WebElement applyDeviceButton = driver.findElement(By.xpath("//div[contains(text(), '完成')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(applyDeviceButton));
			applyDeviceButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Apply device button not clickable.");
		}

		if (isBasic) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text() = '" + propertyID + "']/../td[text() = 'LentOut']")));
			} catch (TimeoutException e) {
				Assert.fail("Not properly logged.");
			}
			LogOut();
		} else {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("negative")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close")));
				WebElement closeIcon = driver.findElement(By.className("close"));
				wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
				closeIcon.click();
			} catch (TimeoutException e) {
				Assert.fail("Error message not shown properly.");
			}

			WebElement cancelButton = driver.findElement(By.xpath("//div[text() = '取消']"));
			cancelButton.click();

			LogOut();
		}
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
