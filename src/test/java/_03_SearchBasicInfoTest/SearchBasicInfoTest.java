package _03_SearchBasicInfoTest;

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
public class SearchBasicInfoTest {
    @Parameter()
    public String query_text;
    @Parameter(value = 1)
    public boolean isBasic;


    public String username = "jun28@pingmao.net";
    public String password = "password";
    private WebDriver driver;

    @Parameterized.Parameters(name = "{index}, query_text = {0}, isBasic = {1}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Manage", true},
                {"ManageManageManageManageManageManage", false},
                {"", false},
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
    public void Query(){
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

        //Query--  enter to project

        WebElement startUseButton = driver.findElement(By.xpath("//a[@href='/project']"));
        try{
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

        //Query -- query in project
        WebElement queryField = driver.findElement(By.xpath("//input[@placeholder='查找...']"));
        queryField.sendKeys(query_text);

        WebElement queryButton = driver.findElement(By.xpath("//button[@class='ui icon button']"));
        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(queryButton));
            queryButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the Query button.");
        }
        // result  match
        WebElement queryTable = driver.findElement(By.xpath("//table[@class='ui single line table']"));
        String expectedId = "2020-4577-D-01";
        if(isBasic) {
            //WebElement queryTable = driver.findElement(By.xpath("//table[@class='ui single line table']"));
            //String expectedId = "2020-4577-D-01";

            try {
                buttonWait.until(ExpectedConditions.textToBePresentInElement(queryTable, expectedId));
            } catch (TimeoutException e) {
                Assert.fail("Failed to Query.");
            }
        }
        else{
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
