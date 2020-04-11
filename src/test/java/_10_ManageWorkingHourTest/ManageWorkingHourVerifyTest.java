package _10_ManageWorkingHourTest;

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

import java.util.Arrays;
import java.util.Collection;

import static common.Constants.*;

@RunWith(Parameterized.class)
public class ManageWorkingHourVerifyTest {
	@Parameter()
	public boolean isBasic;
	@Parameter(value = 1)
	public String functionType;
	@Parameter(value = 2)
	public String activityType;
	@Parameter(value = 3)
	public String startTime;
	@Parameter(value = 4)
	public String endTime;

	@SuppressWarnings("FieldCanBeLocal")
	private final String commonUserUsername = "jie62@hotmail.com";
	@SuppressWarnings("FieldCanBeLocal")
	private final String projectManagerUsername = "jun28@pingmao.net";
	@SuppressWarnings("FieldCanBeLocal")
	private final String password = "password";
	@SuppressWarnings("FieldCanBeLocal")
	private final String projectID = "2020-4577-D-01";
	@SuppressWarnings("FieldCanBeLocal")
	private final String commonUserID = "SYKJ-20200201-0000";


	@Parameterized.Parameters(name = "{index}, isBasic = {0}, functionType = {1}, activityType = {2}, startTime = {3}, endTime = {4}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][]{
				{true, "界面", "工程活动 设计", "2020-04-06 08:00:00", "2020-04-06 16:00:00"},
				{false, "界面", "工程活动 设计", "2020-04-06 08:00:00", "2020-04-06 16:00:00"},
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

	void SubmitWorkingHour() throws InterruptedException {
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

		WebElement projectHourTab = driver.findElement(By.xpath("//a[@href='/projectHour']"));
		projectHourTab.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectHour"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project hour page.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = '管理工时记录']")));
		} catch (TimeoutException e) {
			Assert.fail("Manage working hour record button not found.");
		}

		WebElement manageWorkingHourRecordButton = driver.findElement(By.xpath("//button[text() = '管理工时记录']"));
		manageWorkingHourRecordButton.click();

		WebElement newWorkingHourButton = driver.findElement(By.xpath("//button[text() = '新增']"));
		newWorkingHourButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = '新建工时记录']")));
		} catch (TimeoutException e) {
			Assert.fail("New working hour form not popped up.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择功能类型')]")));
		} catch (TimeoutException e) {
			Assert.fail("Function type field not found.");
		}

		WebElement functionTypeField = driver.findElement(By.xpath("//div/div[contains(text(), '选择功能类型')]/../input"));

		if (functionType.length() != 0) {
			functionTypeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			functionTypeField.sendKeys(Keys.DELETE);
			functionTypeField.sendKeys(functionType);
			functionTypeField.sendKeys(Keys.ENTER);
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择活动类型')]")));
		} catch (TimeoutException e) {
			Assert.fail("Activity type field not found.");
		}

		WebElement activityTypeField = driver.findElement(By.xpath("//div/div[contains(text(), '选择活动类型')]/../input"));

		if (activityType.length() != 0) {
			activityTypeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			activityTypeField.sendKeys(Keys.DELETE);
			activityTypeField.sendKeys(activityType);
			activityTypeField.sendKeys(Keys.ENTER);
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='开始时间：格式为yyyy-MM-dd HH:mm:ss']")));
		} catch (TimeoutException e) {
			Assert.fail("Start time field not found.");
		}
		WebElement startTimeField = driver.findElement(By.xpath("//input[@placeholder='开始时间：格式为yyyy-MM-dd HH:mm:ss']"));

		startTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		startTimeField.sendKeys(Keys.DELETE);
		startTimeField.sendKeys(startTime);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='结束时间：格式为yyyy-MM-dd HH:mm:ss']")));
		} catch (TimeoutException e) {
			Assert.fail("End time field not found.");
		}
		WebElement endTimeField = driver.findElement(By.xpath("//input[@placeholder='结束时间：格式为yyyy-MM-dd HH:mm:ss']"));

		endTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		endTimeField.sendKeys(Keys.DELETE);
		endTimeField.sendKeys(endTime);

		WebElement applyWorkingHourButton = driver.findElement(By.xpath("//div[contains(text(), '完成')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(applyWorkingHourButton));
			applyWorkingHourButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Apply working hour button not clickable.");
		}


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text() = '" + startTime + "']")));
		} catch (TimeoutException e) {
			Assert.fail("Working hour record not found.");
		}

		WebElement submitButton = driver.findElement(By.xpath("//button[text() = '完成']"));
		submitButton.click();

	}

	void VerifyWorkingHourStatus(String status) {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text() = '" + startTime + "']/../td[text() = '" + endTime + "']/../td[text() = '" + status + "']")));
		} catch (TimeoutException e) {
			Assert.fail("Working hour record not correct.");
		}
	}

	void VerifyWorkingHour(boolean isPass) {
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

		WebElement projectHourTab = driver.findElement(By.xpath("//a[@href='/projectHour']"));
		projectHourTab.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectHour"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project hour page.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = '去审核']")));
		} catch (TimeoutException e) {
			Assert.fail("Verify working hour record button not found.");
		}

		WebElement verifyWorkingHourRecordButton = driver.findElement(By.xpath("//button[text() = '去审核']"));
		verifyWorkingHourRecordButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text() = '" + startTime + "']/../td[text() = '" + endTime + "']/../td[text() = '" + activityType + "']/../td[text() = '" + functionType + "']/../td[text() = '" + commonUserID + "']")));
		} catch (TimeoutException e) {
			Assert.fail("Working hour record not correct.");
		}

		WebElement workingHourDetailButton = driver.findElement(By.xpath("//td[text() = '" + startTime + "']/../td[7]/i"));
		workingHourDetailButton.click();

		if (isPass) {
			WebElement verifyWorkingHourSelection = driver.findElement(By.xpath("//label[text() = '通过']"));
			WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
			try {
				wait.until(ExpectedConditions.elementToBeClickable(verifyWorkingHourSelection));
				wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
				verifyWorkingHourSelection.click();
				confirmButton.click();
			} catch (TimeoutException e) {
				Assert.fail("The working hour cannot be approved.");
			}


		} else {
			WebElement verifyWorkingHourSelection = driver.findElement(By.xpath("//label[text() = '不通过']"));
			WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
			try {
				wait.until(ExpectedConditions.elementToBeClickable(verifyWorkingHourSelection));
				wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
				verifyWorkingHourSelection.click();
				confirmButton.click();
			} catch (TimeoutException e) {
				Assert.fail("The working hour cannot be approved.");
			}
		}

		WebElement applyWorkingHourButton = driver.findElement(By.xpath("//button[contains(text(), '完成')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(applyWorkingHourButton));
			applyWorkingHourButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Apply working hour button not clickable.");
		}
	}

	@Test

	public void ManageWorkingHourSubmit() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		SignIn(commonUserUsername, password);
		SubmitWorkingHour();
		VerifyWorkingHourStatus("尚未审核");
		LogOut();
		SignIn(projectManagerUsername, password);
		VerifyWorkingHour(isBasic);
		LogOut();
		SignIn(commonUserUsername, password);
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

		WebElement projectHourTab = driver.findElement(By.xpath("//a[@href='/projectHour']"));
		projectHourTab.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectHour"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project hour page.");
		}
		if (isBasic) {

			VerifyWorkingHourStatus("已审核通过");
		} else {

			VerifyWorkingHourStatus("审核不通过");
		}
		LogOut();
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
