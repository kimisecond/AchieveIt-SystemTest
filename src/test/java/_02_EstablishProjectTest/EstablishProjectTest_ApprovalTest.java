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
public class EstablishProjectTest_ApprovalTest {

    @Parameter()
    public String projectId;
    @Parameter(value = 1)
    public String projectName;
    @Parameter(value = 2)
    public String clientName;
    @Parameter(value = 3)
    public String startTime;
    @Parameter(value = 4)
    public String endTime;
    @Parameter(value = 5)
    public String milestone;
    @Parameter(value = 6)
    public String primaryTech;
    @Parameter(value = 7)
    public String businessArea;
    @Parameter(value = 8)
    public String primaryFunc;
    @Parameter(value = 9)
    public boolean isBasic;

    private WebDriver driver;
    public String username = "jun28@pingmao.net";
    public String password = "password";

    @Parameterized.Parameters(name = "{index}, projectId={0},projectName={1},clientName={2},startTime={3}, endTime={4},milestone={5},primaryTech={6},businessAre={7},primaryFunc={8},isBasic={9}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",true},
                {"2020-2500-D-51","","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-06-30 18:00:00","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-03-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","",
                        "设计,开发,测试,交付","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-05-30 18:00:00",
                        "","springboot","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","","软件开发","Manage",false},
                {"2020-2500-D-51","Yiang_Test","万迅电脑信息有限公司","2020-04-03 18:00:00","2020-05-30 18:00:00",
                        "设计,开发,测试,交付","springboot","软件开发","",false}

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
    public void Approval(){
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

        // enter approval project

        //WebElement approvalButton = driver.findElement(By.xpath("//button[@class='ui button']"));
        WebElement approvalButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/a/button"));
        try{
            buttonWait.until(ExpectedConditions.elementToBeClickable(approvalButton));
            approvalButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the approval button.");
        }

        String Approval_URL = "http://127.0.0.1:5000/setUp";
        try {
            buttonWait.until(ExpectedConditions.urlToBe(Approval_URL));
        } catch (TimeoutException e) {
            Assert.fail("Failed to jump to the approval page.");
        }

        // write information

        WebElement projectIDField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[1]/div/input"));
        WebElement projectNameField = driver.findElement(By.xpath("//input[@placeholder='项目名']"));
        WebElement clientNameField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[3]/div/input"));
        WebElement startTimeField = driver.findElement(By.xpath("//input[@placeholder='开始时间：格式为yyyy-MM-dd HH:mm:ss']"));
        WebElement endTimeField = driver.findElement(By.xpath("//input[@placeholder='结束时间：格式为yyyy-MM-dd HH:mm:ss']"));
        WebElement milestoneField = driver.findElement(By.xpath("//input[@placeholder='里程碑']"));
        WebElement primaryTechField = driver.findElement(By.xpath("//input[@placeholder='主要技术']"));
        WebElement businessAreaField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[8]/div/input"));
        WebElement primaryFuncField = driver.findElement(By.xpath("//input[@placeholder='主要功能']"));

        projectIDField.sendKeys(projectId);
        projectNameField.sendKeys(projectName);
        clientNameField.sendKeys(clientName);
        startTimeField.sendKeys(startTime);
        endTimeField.sendKeys(endTime);
        milestoneField.sendKeys(milestone);
        primaryTechField.sendKeys(primaryTech);
        businessAreaField.sendKeys(businessArea);
        primaryFuncField.sendKeys(primaryFunc);

        WebElement applyButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[10]"));
        /*try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(applyButton));
        } catch (TimeoutException e) {
            Assert.fail("failed to apply.");
        }*/
        applyButton.click();

        if(isBasic){
            try {
                buttonWait.until(ExpectedConditions.urlToBe(Project_URL));
            } catch (TimeoutException e) {
                Assert.fail("Failed to apply for projectApproval.");
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
