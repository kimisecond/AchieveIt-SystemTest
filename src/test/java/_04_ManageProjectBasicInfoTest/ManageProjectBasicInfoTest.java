package _04_ManageProjectBasicInfoTest;

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
public class ManageProjectBasicInfoTest {

    @Parameterized.Parameter()
    public String projectName;
    @Parameterized.Parameter(value = 1)
    public String clientName;
    @Parameterized.Parameter(value = 2)
    public String startTime;
    @Parameterized.Parameter(value = 3)
    public String endTime;
    @Parameterized.Parameter(value = 4)
    public String milestone;
    @Parameterized.Parameter(value = 5)
    public String primaryTech;
    @Parameterized.Parameter(value = 6)
    public String businessArea;
    @Parameterized.Parameter(value = 7)
    public String primaryFunc;
    @Parameterized.Parameter(value = 8)
    public boolean isBasic;

    private WebDriver driver;
    public String username = "jun28@pingmao.net";
    public String password = "password";

    @Parameterized.Parameters(name = "{index},projectName={0},clientName={1},startTime={2}, endTime={3},milestone={4},primaryTech={5},businessAre={6},primaryFunc={7},isBasic={8}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", true},
                {"", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "新宇龙信息科技公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", true},
                {"SimpleManage开发", "MBP软件科技有限公司", "2020-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2019-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "",
                        "设计,开发,测试,交付", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "", "springboot", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "", "软件开发", "Manage", false},
                {"SimpleManage开发", "MBP软件科技有限公司", "2019-11-30 16:00:00", "2020-05-31 16:00:00",
                        "设计,开发,测试,交付", "springboot", "软件开发", "", false}

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
    public void Modify() {


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

        //start to modify project
        WebElement modifyButton = driver.findElement(By.xpath("//button[contains(text(),'修改项目信息')]"));
        try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(modifyButton));
            modifyButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the modify button.");
        }




        // write information

        WebElement projectNameField = driver.findElement(By.xpath("//input[@placeholder='项目名']"));
        WebElement clientNameField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[2]/div/input"));
        WebElement startTimeField = driver.findElement(By.xpath("//input[@placeholder='开始时间：格式为yyyy-MM-dd HH:mm:ss']"));
        WebElement endTimeField = driver.findElement(By.xpath("//input[@placeholder='结束时间：格式为yyyy-MM-dd HH:mm:ss']"));
        WebElement milestoneField = driver.findElement(By.xpath("//input[@placeholder='里程碑']"));
        WebElement primaryTechField = driver.findElement(By.xpath("//input[@placeholder='主要技术']"));
        WebElement businessAreaField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[7]/div/input"));
        WebElement primaryFuncField = driver.findElement(By.xpath("//input[@placeholder='主要功能']"));

        System.out.println(projectName);
        System.out.println(clientName);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(milestone);
        System.out.println(primaryTech);
        System.out.println(businessArea);
        System.out.println(primaryFunc);


        projectNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        projectNameField.sendKeys(Keys.DELETE);
        projectNameField.sendKeys(projectName);

        clientNameField.sendKeys(clientName);

        startTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        startTimeField.sendKeys(Keys.DELETE);
        startTimeField.sendKeys(startTime);

        endTimeField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        endTimeField.sendKeys(Keys.DELETE);
        endTimeField.sendKeys(endTime);

        milestoneField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        milestoneField.sendKeys(Keys.DELETE);
        milestoneField.sendKeys(milestone);

        primaryTechField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        primaryTechField.sendKeys(Keys.DELETE);
        primaryTechField.sendKeys(primaryTech);

        businessAreaField.sendKeys(businessArea);

        primaryFuncField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        primaryFuncField.sendKeys(Keys.DELETE);
        primaryFuncField.sendKeys(primaryFunc);

        WebElement  modifyEndButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div/form/div[10]"));

        /*try {
            buttonWait.until(ExpectedConditions.elementToBeClickable(modifyEndButton));
        } catch (TimeoutException e) {
            Assert.fail("Failed to click the modify button.");
        }*/
        modifyEndButton.click();

        if(isBasic){
            try {

            } catch (TimeoutException e) {
                Assert.fail("Failed modify.");
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