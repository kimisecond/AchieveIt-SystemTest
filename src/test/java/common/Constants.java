package common;

public interface Constants {
	String CHROME_DRIVER_PATH = "src/test/java/resources/drivers/chromedriver.exe";
	Integer TIMEOUT_IN_SECONDS = 20;
	Integer RENDERING_TIMEOUT = 2000; // Added due to scenarios having no adequate ExceptedConditions
	String BASE_URL = "http://localhost:5000/";
}
