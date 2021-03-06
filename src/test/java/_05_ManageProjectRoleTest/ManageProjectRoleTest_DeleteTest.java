package _05_ManageProjectRoleTest;


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
public class ManageProjectRoleTest_DeleteTest {
    @Parameterized.Parameter()
    public int number;
    @Parameterized.Parameter(value = 1)
    public boolean isBasic;
    @Parameterized.Parameter(value = 2)
    public String role;
    @Parameterized.Parameter(value = 3)
    public String id;



    private WebDriver driver;
    public String username = "jun28@pingmao.net";
    public String password = "password";

    @Parameterized.Parameters(name = "{index},number={0},isBasic={1},role = {2}, id = {3}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {0,false,"TestLeader","SYKJ-20200201-0000"},
                {1,true,"TestStaff","SYKJ-20200205-0000"},
                {2,true,"TestLeader","SYKJ-20200201-0000"}
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
    public void Delete() {
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

        //choose project member
        WebElement memberButton = driver.findElement(By.xpath("//a[@href='/projectMember']"));
        memberButton.click();

        //modify member information
        WebElement modifyMemberButton = driver.findElement(By.xpath("//button[contains(text(),'修改项目人员信息')]"));
        modifyMemberButton.click();
        if (number==1) {
            try {
                WebElement staffDeleteButton = driver.findElement((By.xpath("//td[contains(text(),'TestStaff')]/../td[5]/i")));
                buttonWait.until(ExpectedConditions.elementToBeClickable(staffDeleteButton));
                staffDeleteButton.click();
                WebElement ackButton = driver.findElement(By.xpath("//div[contains(text(),'完成')]"));
                ackButton.click();
                WebElement finishButton = driver.findElement(By.xpath("//button[contains(text(),'完成')]"));
                finishButton.click();
            } catch (TimeoutException e) {
                Assert.fail("Failed to click the delete button.");
            }
        }
        else if(number==2){
            try {
                WebElement staffDeleteButton = driver.findElement((By.xpath("//td[contains(text(),'TestLeader')]/../td[5]/i")));
                buttonWait.until(ExpectedConditions.elementToBeClickable(staffDeleteButton));
                staffDeleteButton.click();
                WebElement ackButton = driver.findElement(By.xpath("//div[contains(text(),'完成')]"));
                ackButton.click();
                WebElement finishButton = driver.findElement(By.xpath("//button[contains(text(),'完成')]"));
                finishButton.click();
            } catch (TimeoutException e) {
                Assert.fail("Failed to click the delete button.");
            }
        }
        else if (number==0){
            try {
                WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'取消')]"));
                buttonWait.until(ExpectedConditions.elementToBeClickable(cancelButton));
                cancelButton.click();
            } catch (TimeoutException e) {
                Assert.fail("Failed to click the cancel button.");
            }
        }
    }
    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
