package _06_ManageProjectFunctionListTest;
import common.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;
@RunWith(Parameterized.class)
public class ManageProjectFunctionListTest_SubmitTest {
    @Parameterized.Parameter()
    public String fatherId1;
    @Parameterized.Parameter(value = 1)
    public String description1;
    @Parameterized.Parameter(value = 2)
    public String fatherId2;
    @Parameterized.Parameter(value = 3)
    public String description2;
    @Parameterized.Parameter(value = 4)
    public String fatherId3;
    @Parameterized.Parameter(value = 5)
    public String description3;
    @Parameterized.Parameter(value = 6)
    public boolean isBasic;
    private WebDriver driver;
    public String username = "jun28@pingmao.net";
    public String password = "password";

    @Parameterized.Parameters(name = "{index},fatherId1={0},description1={1},fatherId2={2},description2={3},fatherId3={4},description3={5},isBasic={6}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"self","管理人员权限信息","002","管理人员权限","002","管理人员信息", true},
                {"self","","002","管理人员权限","002","管理人员信息",false},
        });
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);

        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("headless"); // Comment this line when debugging
        driver = new ChromeDriver(chromeOptions);
        driver.get(Constants.BASE_URL);
    }

    @Test
    public void Create() {
        //  SignIn
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

        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the SignIn button.");
        }

        try {
            buttonWait.until(ExpectedConditions.urlToBe(Constants.BASE_URL));
        } catch (TimeoutException e) {
            Assert.fail("Failed to jump to the home page.");
        }

        WebElement startUseButton = driver.findElement(By.xpath("//a[@href='/project']"));
        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(startUseButton));
            startUseButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the StartUse button.");
        }

        String Project_URL = "http://127.0.0.1:5000/project";
        try {
            buttonWait.until(ExpectedConditions.urlToBe(Project_URL));
        } catch (TimeoutException e) {
            Assert.fail("Failed to jump to the query page.");
        }

        //click  project to show some  projects  that need to be modified
        WebElement projectButton = driver.findElement(By.xpath("//a[@href='/project']"));
        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(projectButton));
            projectButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to show projects");
        }


        // enter  project  detail                              //*[@id="root"]/div/div[2]/div/div/div/table/tbody/tr[2]/td[4]/a/
        WebElement detailButton = driver.findElement(By.xpath("//td[contains(text(),'SimpleManage开发')]/../td[4]/a"));
        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(detailButton));
            detailButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the detail button.");
        }

        String Detail_URL = "http://127.0.0.1:5000/projectDetail";
        try {
            buttonWait.until(ExpectedConditions.urlToBe(Detail_URL));
        } catch (TimeoutException e) {
            Assert.fail("Failed to jump to the detail page.");
        }

        //choose project function
        WebElement functionButton = driver.findElement(By.xpath("//a[@href='/projectFunction']"));
        functionButton.click();

        WebElement modifyFuncButton = driver.findElement(By.xpath("//button[contains(text(),'修改项目功能')]"));
        modifyFuncButton.click();

        WebElement createFuncButton = driver.findElement(By.xpath("//button[contains(text(),'创建新功能')]"));
        createFuncButton.click();

        WebElement fatherIdField = driver.findElement(By.xpath("//label[contains(text(),'父功能ID')]/../div/input"));
        WebElement descriptionField = driver.findElement(By.xpath("//input[@placeholder='功能描述']"));

        fatherIdField.sendKeys(fatherId1);
        descriptionField.sendKeys(description1);

        WebElement ackButton = driver.findElement(By.xpath("//div[contains(text(),'完成')]"));
        ackButton.click();

        if(isBasic){
            try {
                WebElement finishButton = driver.findElement(By.xpath("//button[contains(text(),'完成')]"));
                finishButton.click();
            } catch (TimeoutException e) {
                Assert.fail("Failed to click the finish button.");
            }
        }
        else {
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
