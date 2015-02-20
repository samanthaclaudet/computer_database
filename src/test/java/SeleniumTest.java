import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.excilys.cdb.utilsdb.DatabaseLoader;

public class SeleniumTest {
	private static WebDriver driver;
	private static String baseUrl;

	@BeforeClass
	public static void setUp() {
		DatabaseLoader.INSTANCE.load();
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/cdb/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl);
	}

	@Test
	public void testSelenium() throws Exception {

		WebElement resultTab = driver.findElement(By.id("results"));
		List<WebElement> results = resultTab.findElements(By.tagName("tr"));
		//WebElement searchbox = driver.findElement(By.id("searchbox"));
		//WebElement searchsubmit = driver.findElement(By.id("searchsubmit"));
		Assert.assertEquals(10, results.size());
		// searchbox.clear();
		// searchbox.sendKeys("test");
		// searchsubmit.click();
		// resultTab = driver.findElement(By.id("results"));
		// results = resultTab.findElements(By.tagName("tr"));
		// Assert.assertEquals(5, results.size());
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

}
