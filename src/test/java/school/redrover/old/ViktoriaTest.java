package school.redrover.old;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class ViktoriaTest {

    @Test
    public void testGetTile() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");
        Assert.assertEquals(driver.getTitle(),"Automation Exercise");
        driver.quit();
    }
}
