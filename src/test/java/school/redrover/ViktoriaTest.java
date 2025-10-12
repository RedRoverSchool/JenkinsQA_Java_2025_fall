package school.redrover;

import com.sun.source.tree.AssertTree;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViktoriaTest {

    @Test
    public void testGetTile() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");
        Assert.assertEquals(driver.getTitle(),"Automation Exercise");
        driver.quit();
    }
}
