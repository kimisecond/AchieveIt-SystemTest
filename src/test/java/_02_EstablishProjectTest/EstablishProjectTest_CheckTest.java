package _02_EstablishProjectTest;
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
public class EstablishProjectTest_CheckTest {
    @Parameter()
    public int number;
    @Parameter(value=1)
    public String check;

    private WebDriver driver;

    public String manager_username = "jun28@pingmao.net";
    public String superior_username = "xiuying41@leizhu.cn";
    public String password = "password";


    @Parameterized.Parameters(name = "{index}, number = {0}, check = {1}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {0,"通过"},
                {1,"不通过"}
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
    public void Check(){
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


        usernameField.sendKeys(superior_username);
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

        WebElement projectButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/a[2]"));
        projectButton.click();
        projectButton.click();
        projectButton.click();
        projectButton.click();
        projectButton.click();


        if(number == 0){
            WebElement detailButton = driver.findElement(By.xpath("//td[contains(text(),'Yiang_Test')]/../td[4]/a"));
            try {
                buttonWait.until(ExpectedConditions.elementToBeClickable(detailButton));
                detailButton.click();
            } catch (TimeoutException e) {
                Assert.fail("Failed to click the detail button.");
            }
            WebElement projectUpdateButton = driver.findElement(By.xpath("//button[contains(text(),'推进项目状态')]"));
            projectUpdateButton.click();
            //WebElement noButton = driver.findElement(By.xpath("//input[@value='false']"));
            //WebElement noButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/div[2]/div[2]/div/form/div[1]/div/input"));
            WebElement noButton = driver.findElement(By.xpath("//label[contains(text(),'不通过')]"));
            //WebElement noButton = driver.findElement(By.xpath("//label[contains(text(),'不通过')]"));

            noButton.click();
            WebElement ackButton = driver.findElement(By.xpath("//div[contains(text(),'确认')]"));
            ackButton.click();

            /*
            WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div/a"));
            logoutButton.click();

            goToSignInPage.click();
            usernameField.sendKeys(manager_username);
            passwordField.sendKeys(password);
            startUseButton.click();

            projectButton.click();

            String expectedState = "Rejected";
            WebElement queryTable = driver.findElement(By.xpath("//table[@class='ui single line table']"));

            try {
                buttonWait.until(ExpectedConditions.textToBePresentInElement(queryTable, expectedState));
            } catch (TimeoutException e) {
                Assert.fail("Failed to Query.");
            }*/
        }
    }
    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
