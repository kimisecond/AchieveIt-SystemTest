package _07_ManageProjectStatusTest;

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
import java.util.List;

import static common.Constants.*;

@RunWith(Parameterized.class)
public class ManageProjectStatusTest {
	@Parameter()
	public boolean isBasic;
	@Parameter(value = 1)
	public String projectID;
	@Parameter(value = 2)
	public String projectName;
	@Parameter(value = 3)
	public String customerInfo;
	@Parameter(value = 4)
	public String startTime;
	@Parameter(value = 5)
	public String endTime;
	@Parameter(value = 6)
	public String milestone;
	@Parameter(value = 7)
	public String mainTech;
	@Parameter(value = 8)
	public String businessField;
	@Parameter(value = 9)
	public String mainFeature;
	@Parameter(value = 10)
	public String memberID;
	@Parameter(value = 11)
	public String superiorID;
	@Parameter(value = 12)
	public String projectRole;

	@SuppressWarnings("FieldCanBeLocal")
	private final String projectManagerUsername = "jun28@pingmao.net";
	@SuppressWarnings("FieldCanBeLocal")
	private final String projectSuperiorUsername = "xiuying41@leizhu.cn";
	@SuppressWarnings("FieldCanBeLocal")
	private final String qaLeaderUsername = "gangyao@hotmail.com";
	@SuppressWarnings("FieldCanBeLocal")
	private final String epgLeaderUsername = "gang66@guiyingguo.cn";
	@SuppressWarnings("FieldCanBeLocal")
	private final String configurationManagerUsername = "mpan@hotmail.com";
	@SuppressWarnings("FieldCanBeLocal")
	private final String password = "password";


	@Parameterized.Parameters(name = "{index}, isBasic = {0}, projectID = {1}, projectName = {2}, customerInfo = {3}, startTime = {4}, endTime = {5}, milestone = {6}, mainTech = {7}, businessField = {8}, mainFeature = {9}, memberID = {10}, superiorID = {11}, projectRole = {12}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][]{
				{true, "2020-1251-D-85", "123", "新格林耐特传媒有限公司", "2020-04-10 12:00:00", "2020-06-10 12:00:00", "123", "123", "软件开发", "123", "SYKJ-20200201-0000", "SYKJ-20200101-0000", "DevelopmentLeader"},
				{false, "2020-2500-D-51", "123", "新格林耐特传媒有限公司", "2020-04-10 12:00:00", "2020-06-10 12:00:00", "123", "123", "软件开发", "123", "SYKJ-20200201-0000", "SYKJ-20200101-0000", "DevelopmentLeader"},
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

	void SignIn(String username, @SuppressWarnings("SameParameterValue") String password) {
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

	void ApplyProject() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		WebElement projectInfoTab = driver.findElement(By.xpath("//button[text() = '立项']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectInfoTab));
			projectInfoTab.click();
		} catch (TimeoutException e) {
			Assert.fail("Set up button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "setUp"));
		} catch (TimeoutException e) {
			Assert.fail("Set up page not jumped to properly.");
		}

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h4[contains(text(), '立项相关信息')]")));
		} catch (TimeoutException e) {
			Assert.fail("Form not opened.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择项目ID')]")));
		} catch (TimeoutException e) {
			Assert.fail("Project ID field not found.");
		}
		WebElement projectIDField = driver.findElement(By.xpath("//div/div[contains(text(), '选择项目ID')]/../input"));

		projectIDField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		projectIDField.sendKeys(Keys.DELETE);
		projectIDField.sendKeys(projectID);
		projectIDField.sendKeys(Keys.ENTER);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='项目名']")));
		} catch (TimeoutException e) {
			Assert.fail("Project name field not found.");
		}
		WebElement projectNameField = driver.findElement(By.xpath("//input[@placeholder='项目名']"));

		projectNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		projectNameField.sendKeys(Keys.DELETE);
		projectNameField.sendKeys(projectName);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择客户')]")));
		} catch (TimeoutException e) {
			Assert.fail("Customer name field not found.");
		}
		WebElement customerInfoField = driver.findElement(By.xpath("//div/div[contains(text(), '选择客户')]/../input"));

		customerInfoField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		customerInfoField.sendKeys(Keys.DELETE);
		customerInfoField.sendKeys(customerInfo);
		customerInfoField.sendKeys(Keys.ENTER);

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

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='里程碑']")));
		} catch (TimeoutException e) {
			Assert.fail("Milestone field not found.");
		}
		WebElement milestoneField = driver.findElement(By.xpath("//input[@placeholder='里程碑']"));

		milestoneField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		milestoneField.sendKeys(Keys.DELETE);
		milestoneField.sendKeys(milestone);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='主要技术']")));
		} catch (TimeoutException e) {
			Assert.fail("Main tech field not found.");
		}
		WebElement mainTechField = driver.findElement(By.xpath("//input[@placeholder='主要技术']"));

		mainTechField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		mainTechField.sendKeys(Keys.DELETE);
		mainTechField.sendKeys(mainTech);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择业务领域')]")));
		} catch (TimeoutException e) {
			Assert.fail("Business field field not found.");
		}
		WebElement businessFieldField = driver.findElement(By.xpath("//div/div[contains(text(), '选择业务领域')]/../input"));

		businessFieldField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		businessFieldField.sendKeys(Keys.DELETE);
		businessFieldField.sendKeys(businessField);
		businessFieldField.sendKeys(Keys.ENTER);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='主要功能']")));
		} catch (TimeoutException e) {
			Assert.fail("Main tech field not found.");
		}
		WebElement mainFeatureField = driver.findElement(By.xpath("//input[@placeholder='主要功能']"));

		mainFeatureField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		mainFeatureField.sendKeys(Keys.DELETE);
		mainFeatureField.sendKeys(mainFeature);

		WebElement applyProjectButton = driver.findElement(By.xpath("//div[contains(text(), '申请立项')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(applyProjectButton));
			applyProjectButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Apply project button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "project"));
		} catch (TimeoutException e) {
			Assert.fail("Jump to project page failed.");
		}

		Thread.sleep(RENDERING_TIMEOUT);

		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		//	Verify the existence of the applied project
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + projectID + "')]")));
		} catch (TimeoutException e) {
			Assert.fail("Applied project not found");
		}

		//	Verify the status of the applied project
		WebElement currentProjectStatus = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[3]"));
		Assert.assertEquals("Applied", currentProjectStatus.getText());
	}

	void DisapproveProject() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Applied')]")));
		} catch (TimeoutException e) {
			Assert.fail("Target project not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//a[@href='/projectDetail']"));
		projectDetailButton.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDetail"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project detail page.");
		}

		WebElement pushProjectStatusButton = driver.findElement(By.xpath("//button[contains(text(), '推进项目状态')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pushProjectStatusButton));
			pushProjectStatusButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Push project status button not clickable.");
		}

		WebElement disapproveSelection = driver.findElement(By.xpath("//label[contains(text(), '不通过')]"));
		WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(disapproveSelection));
			wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
			disapproveSelection.click();
			confirmButton.click();
		} catch (TimeoutException e) {
			Assert.fail("The project cannot be approved.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("positive")));
			WebElement closeIcon = driver.findElement(By.className("close"));
			wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
			closeIcon.click();
		} catch (TimeoutException e) {
			Assert.fail("Approval message not shown properly.");
		}
	}

	void ApproveProject() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Applied')]")));
		} catch (TimeoutException e) {
			Assert.fail("Target project not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//a[@href='/projectDetail']"));
		projectDetailButton.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDetail"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project detail page.");
		}

		WebElement pushProjectStatusButton = driver.findElement(By.xpath("//button[contains(text(), '推进项目状态')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pushProjectStatusButton));
			pushProjectStatusButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Push project status button not clickable.");
		}

		WebElement approveSelection = driver.findElement(By.xpath("//label[text() = '通过']"));
		WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(approveSelection));
			wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
			approveSelection.click();
			confirmButton.click();
		} catch (TimeoutException e) {
			Assert.fail("The project cannot be approved.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("positive")));
			WebElement closeIcon = driver.findElement(By.className("close"));
			wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
			closeIcon.click();
		} catch (TimeoutException e) {
			Assert.fail("Approval message not shown properly.");
		}
	}

	void VerifyProjectStatus(String statusName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		//  Verify project status
		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		Thread.sleep(RENDERING_TIMEOUT);

		//	Verify the existence of the applied project
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + projectID + "')]")));
		} catch (TimeoutException e) {
			Assert.fail("Applied project not found");
		}

		//	Verify the status of the applied project
		WebElement currentProjectStatus = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[3]"));
		Assert.assertEquals(statusName, currentProjectStatus.getText());
	}

	void ImportProjectFunction() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Initiated')]")));
		} catch (TimeoutException e) {
			Assert.fail("Target project not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[4]/a"));
		projectDetailButton.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDetail"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project detail page.");
		}

		WebElement projectFunctionTab = driver.findElement(By.xpath("//a[@href='/projectFunction']"));
		projectFunctionTab.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectFunction"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project function page.");
		}

		WebElement modifyProjectFunction = driver.findElement(By.xpath("//button[contains(text(), '修改项目功能')]"));
		modifyProjectFunction.click();

		WebElement createNewFunction = driver.findElement(By.xpath("//button[contains(text(), '创建新功能')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(createNewFunction));
			createNewFunction.click();
		} catch (TimeoutException e) {
			Assert.fail("New function form not opened");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择新功能的父功能')]")));
		} catch (TimeoutException e) {
			Assert.fail("Parent selection not found");
		}
		WebElement parentFunctionSelectionField = driver.findElement(By.xpath("//div[contains(text(), '选择新功能的父功能')]/../input"));

		parentFunctionSelectionField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		parentFunctionSelectionField.sendKeys(Keys.DELETE);
		parentFunctionSelectionField.sendKeys("self");
		parentFunctionSelectionField.sendKeys(Keys.ENTER);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='功能描述']")));
		} catch (TimeoutException e) {
			Assert.fail("Function description field not found");
		}
		WebElement functionDescriptionField = driver.findElement(By.xpath("//input[@placeholder='功能描述']"));

		functionDescriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		functionDescriptionField.sendKeys(Keys.DELETE);
		functionDescriptionField.sendKeys("管理人员信息权限");
		functionDescriptionField.sendKeys(Keys.ENTER);

		WebElement submitButton = driver.findElement(By.xpath("//div[contains(text(), '完成')]"));

		try {
			wait.until(ExpectedConditions.elementToBeClickable(submitButton));
			submitButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Function not submitted.");
		}

		submitButton = driver.findElement(By.xpath("//button[contains(text(), '完成')]"));
		submitButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '管理人员信息权限')]/../td[contains(text(), '001')]")));
		} catch (TimeoutException e) {
			Assert.fail("Function id and function description not set properly.");
		}
	}

	void ImportMember(String exactRole) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Initiated')]")));
		} catch (TimeoutException e) {
			Assert.fail("Target project not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[4]/a"));
		projectDetailButton.click();

		try {
			wait.until(ExpectedConditions.urlToBe(BASE_URL + "projectDetail"));
		} catch (TimeoutException e) {
			Assert.fail("Not jumped to project detail page.");
		}

		WebElement projectMemberTab = driver.findElement(By.xpath("//a[@href = '/projectMember']"));
		projectMemberTab.click();

		WebElement modifyProjectMemberInfoButton = driver.findElement(By.xpath("//button[contains(text(), '修改项目人员信息')]"));
		modifyProjectMemberInfoButton.click();

		WebElement importProjectMemberButton = driver.findElement(By.xpath("//button[contains(text(), '导入新成员')]"));
		importProjectMemberButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择人员导入')]")));
		} catch (TimeoutException e) {
			Assert.fail("Member ID field not found.");
		}
		WebElement memberIDField = driver.findElement(By.xpath("//div[contains(text(), '选择人员导入')]/../input"));

		memberIDField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		memberIDField.sendKeys(Keys.DELETE);
		memberIDField.sendKeys(memberID);
		memberIDField.sendKeys(Keys.ENTER);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择该人员在项目中的角色')]")));
		} catch (TimeoutException e) {
			Assert.fail("Project role selection field not found.");
		}
		WebElement projectRoleSelectionField = driver.findElement(By.xpath("//div[contains(text(), '选择该人员在项目中的角色')]/../input"));

		projectRoleSelectionField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		projectRoleSelectionField.sendKeys(Keys.DELETE);
		projectRoleSelectionField.sendKeys(exactRole);
		projectRoleSelectionField.sendKeys(Keys.ENTER);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '选择导入人员的上级')]")));
		} catch (TimeoutException e) {
			Assert.fail("Superior ID field not found.");
		}
		WebElement superiorIDField = driver.findElement(By.xpath("//div[contains(text(), '选择导入人员的上级')]/../input"));

		superiorIDField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		superiorIDField.sendKeys(Keys.DELETE);
		superiorIDField.sendKeys(superiorID);
		superiorIDField.sendKeys(Keys.ENTER);

		WebElement submitButton = driver.findElement(By.xpath("//div[contains(text(), '完成')]"));
		submitButton.click();

		submitButton = driver.findElement(By.xpath("//button[contains(text(), '完成')]"));
		submitButton.click();

		Thread.sleep(RENDERING_TIMEOUT);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + exactRole + "')]")));
			List<WebElement> memberList = driver.findElements(By.xpath("//td[contains(text(), '" + exactRole + "')]/../td"));
			Assert.assertEquals(memberList.get(1).getText(), memberID);
			Assert.assertEquals(memberList.get(2).getText(), superiorID);
		} catch (TimeoutException e) {
			Assert.fail("Member not found.");
		}
	}

	void ConfirmProjectConfiguration() {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);

		WebElement projectTopButton = driver.findElement(By.xpath("//a[@href='/project']"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(projectTopButton));
			projectTopButton.click();
		} catch (TimeoutException e) {
			Assert.fail("Project top button not clickable.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Initiated')]")));
		} catch (TimeoutException e) {
			Assert.fail("Target project not found.");
		}

		WebElement projectDetailButton = driver.findElement(By.xpath("//td[contains(text(), '" + projectID + "')]/../td[4]/a"));
		projectDetailButton.click();

		WebElement confirmProjectConfigurationButton = driver.findElement(By.xpath("//button[contains(text(), '确认项目配置库')]"));
		confirmProjectConfigurationButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("positive")));
			WebElement closeIcon = driver.findElement(By.className("close"));
			wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
			closeIcon.click();
		} catch (TimeoutException e) {
			Assert.fail("Approval message not shown properly.");
		}
	}

	void PushForwardProjectStatus() {
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

		WebElement pushForwardProjectStatusButton = driver.findElement(By.xpath("//button[contains(text(), '推进项目状态')]"));
		pushForwardProjectStatusButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '确认')]")));
		} catch (TimeoutException e) {
			Assert.fail("Push status confirmation alert not found.");
		}

		WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
		confirmButton.click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("positive")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close")));
			WebElement closeIcon = driver.findElement(By.className("close"));
			wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
			closeIcon.click();
		} catch (TimeoutException e) {
			Assert.fail("Approval message not shown properly.");
		}
	}

	void ConfirmArchive() {
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

		WebElement pushForwardProjectStatusButton = driver.findElement(By.xpath("//button[contains(text(), '推进项目状态')]"));
		pushForwardProjectStatusButton.click();

		WebElement approveSelection = driver.findElement(By.xpath("//label[text() = '通过']"));
		WebElement confirmButton = driver.findElement(By.xpath("//div[contains(text(), '确认')]"));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(approveSelection));
			wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
			approveSelection.click();
			confirmButton.click();
		} catch (TimeoutException e) {
			Assert.fail("The project cannot be approved.");
		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("positive")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close")));
			WebElement closeIcon = driver.findElement(By.className("close"));
			wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
			closeIcon.click();
		} catch (TimeoutException e) {
			Assert.fail("Approval message not shown properly.");
		}
	}

	@Test
	public void ManageProjectStatus() throws Exception {
		//	Apply Project
		SignIn(projectManagerUsername, password);
		ApplyProject();
		LogOut();

		//  Verify Project
		SignIn(projectSuperiorUsername, password);
		if (isBasic) {
			ApproveProject();
			LogOut();
			SignIn(projectManagerUsername, password);
			VerifyProjectStatus("Initiated");
			ImportProjectFunction();
			ImportMember(projectRole);
			LogOut();
			SignIn(qaLeaderUsername, password);
			ImportMember("QaStaff");
			LogOut();
			SignIn(epgLeaderUsername, password);
			ImportMember("EPG");
			LogOut();
			SignIn(configurationManagerUsername, password);
			ConfirmProjectConfiguration();
			LogOut();
			SignIn(projectManagerUsername, password);
			PushForwardProjectStatus();
			VerifyProjectStatus("Developing");
			PushForwardProjectStatus();
			VerifyProjectStatus("Delivered");
			PushForwardProjectStatus();
			VerifyProjectStatus("Finished");
			PushForwardProjectStatus();
			VerifyProjectStatus("ReadyArchive");
			LogOut();
			SignIn(configurationManagerUsername, password);
			ConfirmArchive();
			LogOut();
			SignIn(projectManagerUsername, password);
			VerifyProjectStatus("Archived");

		} else {
			DisapproveProject();
			LogOut();
			SignIn(projectManagerUsername, password);
			VerifyProjectStatus("Rejected");
			LogOut();
		}
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
