package _08_ManageProjectRiskInfoTest;


import common.Constants;
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

import static common.Constants.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@RunWith(Parameterized.class)
public class ManageProjectRiskInfoTest {

	@Parameter()
	public boolean isBasic;
	@Parameter(value = 1)
	public String riskType;
	@Parameter(value = 2)
	public String riskDescription;
	@Parameter(value = 3)
	public String riskLevel;
	@Parameter(value = 4)
	public String riskImpactLevel;
	@Parameter(value = 5)
	public String riskStrategy;
	@Parameter(value = 6)
	public String riskStatus;
	@Parameter(value = 7)
	public String riskResponsiblePerson;
	@Parameter(value = 8)
	public String riskFollowingFrequency;
	@Parameter(value = 9)
	public String riskRelatedPerson;

	@Parameterized.Parameters(name = "{index}, isBasic = {0}, riskType = {1}, riskDescription = {2}, riskLevel = {3}, riskImpactLevel = {4}, riskStrategy = {5}, riskStatus = {6}, riskResponsiblePerson = {7}, riskFollowingFrequency = {8}, riskRelatedPerson = {9}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][]{
				{true, "ST", "需求变更", "M", "M", "沟通", "已识别", "SYKJ-20200201-0000", "3", "SYKJ-20200201-0000"},
				{false, "ST", "", "M", "M", "沟通", "已识别", "", "3", "SYKJ-20200201-0000"},
				{false, "ST", "", "M", "M", "沟通", "已识别", "SYKJ-20200201-0000", "3", "SYKJ-20200201-0000"},
				{false, "ST", "需求变更", "M", "M", "", "已识别", "SYKJ-20200201-0000", "3", ""},
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

	void SignIn() {
		WebElement goToSignInPage = driver.findElement(By.xpath("//a[@href='/login']"));

		WebDriverWait buttonWait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		// Wait until the "login" button is clickable
		buttonWait.until(ExpectedConditions.elementToBeClickable(goToSignInPage));

		goToSignInPage.click();

		WebElement usernameField = driver.findElement(By.xpath("//input[@placeholder='用户名']"));
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='密码']"));

		usernameField.sendKeys("jun28@pingmao.net");
		passwordField.sendKeys("password");

		WebElement signInButton = driver.findElement(By.xpath("//button"));

		buttonWait.until(ExpectedConditions.elementToBeClickable(signInButton));
		signInButton.click();

		buttonWait.until(ExpectedConditions.urlToBe(BASE_URL));

		WebElement logoutButton = driver.findElement(By.xpath("//div[@class='right menu']"))
				.findElement(By.xpath("//a[@class='item']"));
		buttonWait.until(ExpectedConditions.elementToBeClickable(logoutButton));
		logoutButton.click();
	}

	void JumpToProjectRisk() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectButton));
			projectButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project button not clickable.");
		}

		Thread.sleep(RENDERING_TIMEOUT);

		List<WebElement> projectList = driver.findElements(By.xpath("//table[@class='ui single line table']/tbody/tr"));

		for (WebElement trList : projectList) {
			if (trList.getText().contains("2020-4577-D-01")) {
				WebElement projectDetailButton = trList.findElement(By.xpath("//a[@href='/projectDetail']"));

				try {
					wait.until(ExpectedConditions.elementToBeClickable(projectDetailButton));
					projectDetailButton.click();
				} catch (TimeoutException e) {
					Assert.fail("Project Detail button not clickable.");
				}
				break;
			}
		}

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDetail"));
		} catch (TimeoutException e) {
			Assert.fail("Failed to jump to project detail page.");
		}

		WebElement projectRiskButton = driver.findElement(By.xpath("//a[@href='/projectRisk']"));

		projectRiskButton.click();

		Thread.sleep(RENDERING_TIMEOUT);

	}

	@Test
	public void ManageProjectRiskInfo() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		//	Sign in
		SignIn();

		//	jump to Project page
		JumpToProjectRisk();

		WebElement createRiskButton = driver.findElement(By.xpath("//button[contains(text(), '新增风险记录')]"));

		try {
			wait.until(ExpectedConditions.elementToBeClickable(createRiskButton));
			createRiskButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Risk form not callable.");
		}

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), '新增风险')]")));
		} catch (TimeoutException e) {
			Assert.fail("Form not opened.");
		}


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择风险类型')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk type field not found.");
		}
		WebElement riskTypeField = driver.findElement(By.xpath("//div/div[contains(text(), '选择风险类型')]/../input"));

		riskTypeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskTypeField.sendKeys(Keys.DELETE);
		riskTypeField.sendKeys(riskType);
		riskTypeField.sendKeys(Keys.ENTER);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='描述']")));
		} catch (TimeoutException e) {
			Assert.fail("Risk description field not found.");
		}
		WebElement riskDescriptionField = driver.findElement(By.xpath("//input[@placeholder='描述']"));

		riskDescriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskDescriptionField.sendKeys(Keys.DELETE);
		riskDescriptionField.sendKeys(riskDescription);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择风险级别')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk level field not found.");
		}
		WebElement riskLevelField = driver.findElement(By.xpath("//div[contains(text(), '选择风险级别')]/../input"));

		riskLevelField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskLevelField.sendKeys(Keys.DELETE);
		riskLevelField.sendKeys(riskLevel);
		riskLevelField.sendKeys(Keys.ENTER);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择风险影响度')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk impact level field not found.");
		}
		WebElement riskImpactLevelField = driver.findElement(By.xpath("//div[contains(text(), '选择风险影响度')]/../input"));

		riskImpactLevelField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskImpactLevelField.sendKeys(Keys.DELETE);
		riskImpactLevelField.sendKeys(riskImpactLevel);
		riskImpactLevelField.sendKeys(Keys.ENTER);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='应对策略']")));
		} catch (TimeoutException e) {
			Assert.fail("Risk strategy field not found.");
		}
		WebElement riskStrategyField = driver.findElement(By.xpath("//input[@placeholder='应对策略']"));

		riskStrategyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskStrategyField.sendKeys(Keys.DELETE);
		riskStrategyField.sendKeys(riskStrategy);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择风险状态')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk status field not found.");
		}
		WebElement riskStatusField = driver.findElement(By.xpath("//div[contains(text(), '选择风险状态')]/../input"));

		riskStatusField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskStatusField.sendKeys(Keys.DELETE);
		riskStatusField.sendKeys(riskStatus);
		riskStatusField.sendKeys(Keys.ENTER);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择风险跟踪频度')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk following frequency field not found.");
		}
		WebElement riskFollowingFrequencyField = driver.findElement(By.xpath("//div[contains(text(), '选择风险跟踪频度')]/../input"));

		riskFollowingFrequencyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskFollowingFrequencyField.sendKeys(Keys.DELETE);
		riskFollowingFrequencyField.sendKeys(riskFollowingFrequency);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择负责人')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk responsible person field not found.");
		}
		WebElement riskResponsiblePersonField = driver.findElement(By.xpath("//div[contains(text(), '选择负责人')]/../input"));

		riskResponsiblePersonField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskResponsiblePersonField.sendKeys(Keys.DELETE);
		riskResponsiblePersonField.sendKeys(riskResponsiblePerson);
		riskResponsiblePersonField.sendKeys(Keys.ENTER);


		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择相关人员')]")));
		} catch (TimeoutException e) {
			Assert.fail("Risk responsible person field not found.");
		}
		WebElement riskRelatedPersonField = driver.findElement(By.xpath("//div[contains(text(), '选择相关人员')]/../input"));

		riskRelatedPersonField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		riskRelatedPersonField.sendKeys(Keys.DELETE);
		riskRelatedPersonField.sendKeys(riskRelatedPerson);
		riskRelatedPersonField.sendKeys(Keys.ENTER);


		try {
			WebElement submitButton = driver.findElement(By.xpath("//div[contains(text(), '完成')]"));
			wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			submitButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Submit button not clickable");
		}

		if (isBasic) {
			Thread.sleep(RENDERING_TIMEOUT);
			int rowCount = driver.findElements(By.xpath("//table[@class='ui celled padded table']/tbody/tr")).size();

			for (int i = 1; i <= 10; ++i) {
				String sCellValue = driver.findElement(By.xpath("//table[@class='ui celled padded table']/tbody/tr["
						+ rowCount + "]/td[" + i + "]")).getText();

				switch (i) {
					case 2:
						Assert.assertEquals(sCellValue, riskType);
						break;
					case 3:
						Assert.assertEquals(sCellValue, riskDescription);
						break;
					case 4:
						Assert.assertEquals(sCellValue, riskLevel);
						break;
					case 5:
						Assert.assertEquals(sCellValue, riskImpactLevel);
						break;
					case 6:
						Assert.assertEquals(sCellValue, riskStrategy);
						break;
					case 7:
						Assert.assertEquals(sCellValue, riskStatus);
						break;
					case 8:
						Assert.assertEquals(sCellValue, "每周" + riskFollowingFrequency + "次");
						break;
					case 9:
						Assert.assertEquals(sCellValue, riskResponsiblePerson);
						break;
					case 10:
						Assert.assertEquals(sCellValue, riskRelatedPerson);
						break;
					default:
						break;
				}
			}


			try {
				WebElement logoutButton = driver.findElement(By.xpath("//div[@class='right menu']"))
						.findElement(By.xpath("//a[@class='item']"));
				wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
				logoutButton.click();
			} catch (TimeoutException e) {
				Assert.fail("Failed to logout.");
			}

		} else {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("negative")));
				WebElement closeIcon = driver.findElement(By.className("close"));
				wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
				closeIcon.click();
			} catch (TimeoutException e) {
				Assert.fail("Error message not shown properly.");
			}

			try {
				WebElement cancelButton = driver.findElement(By.xpath("//div[contains(text(), '取消')]"));
				wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
				cancelButton.click();
			} catch (TimeoutException e) {
				Assert.fail("Cancel button not clickable");
			}

			try {
				WebElement logoutButton = driver.findElement(By.xpath("//div[@class='right menu']"))
						.findElement(By.xpath("//a[@class='item']"));
				wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
				logoutButton.click();
			} catch (TimeoutException e) {
				Assert.fail("Failed to logout.");
			}
		}
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
