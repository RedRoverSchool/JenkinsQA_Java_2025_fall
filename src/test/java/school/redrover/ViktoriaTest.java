package school.redrover;

import com.sun.source.tree.AssertTree;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ViktoriaTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.isSuccess()) {
            driver.quit();
            System.out.println("Test passed :" + result.getName());
        }
        else {
            System.out.println("Test failed :" + result.getName());
        }

    }

    @Test
    public void testGetTile() {
        Assert.assertEquals(driver.getTitle(),"Automation Exercise");

    }

    @Test
    public void testGetUrl() {
        Assert.assertEquals(driver.getCurrentUrl() ,"https://automationexercise.com/");

    }

}
