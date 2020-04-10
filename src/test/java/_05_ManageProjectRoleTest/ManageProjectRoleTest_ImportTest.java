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
public class ManageProjectRoleTest_ImportTest {
    @Parameterized.Parameter()
    public String memberId;
    @Parameterized.Parameter(value = 1)
    public String role;
    @Parameterized.Parameter(value = 2)
    public String superior;
    @Parameterized.Parameter(value = 3)
    public boolean isBasic;

    private WebDriver driver;
    public String username = "jun28@pingmao.net";
    public String password = "password";

    @Parameterized.Parameters(name = "{index}, memberId={0},role={1},superior={2},isBasic={3}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"SYKJ-20200201-0000","TestLeader","SYKJ-20200201-0000",true},
                {"SYKJ-20200205-0000","TestStaff","SYKJ-20200201-0000",true}
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
    public void Import(){
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


        // enter  project  detail
        WebElement detailButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/table/tbody/tr[2]/td[4]/a"));
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

        //import member
        WebElement importMemberButton = driver.findElement(By.xpath("//button[contains(text(),'导入新成员')]"));
        importMemberButton.click();

        WebElement memberIdField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/form/div[1]/div/input"));
        WebElement roleField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/form/div[2]/div/input"));
        WebElement superiorField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/div[2]/div/div[2]/div/form/div[3]/div/input"));

        memberIdField.sendKeys(memberId);
        roleField.sendKeys(role);
        superiorField.sendKeys(superior);
        superiorField.sendKeys(Keys.ENTER);


        WebElement finishImportButton = driver.findElement(By.xpath("//div[contains(text(),'完成')]"));
        //WebElement finishImportButton =driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/div[2]/div/div[3]/div[2]"));
        finishImportButton.click();

        WebElement finishButton = driver.findElement(By.xpath("//button[contains(text(),'完成')]"));
        finishButton.click();

        WebElement memberTable = driver.findElement(By.xpath("//table[@class='ui celled padded table']"));
        if(isBasic) {
            try {
                //buttonWait.until(ExpectedConditions.textToBePresentInElement(memberTable, memberId));
            } catch (TimeoutException e) {
                Assert.fail("Failed to import member.");
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
