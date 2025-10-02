package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BelyaevVTest {

    @Test
    public void testTasksArea() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        new Actions(driver).doubleClick(searchArea).perform();

        String foundSearchTitle = driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();
    }

    @Test
    public void testAddCompareProduct() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://naveenautomationlabs.com/opencart/");

        driver.findElement(By.linkText("Cameras")).click();

        WebElement cameraCanon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='30']"));
        cameraCanon.click();

        Thread.sleep(1000);

        WebElement cameraNikon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='31']"));
        cameraNikon.click();

        Thread.sleep(1000);

        driver.findElement(By.linkText("Product Compare (2)")).click();

        String titleCanon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
        String titleNikon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();

        String compareCameras = titleCanon + " " + titleNikon;

        Assert.assertEquals(compareCameras, "Canon EOS 5D Nikon D300");

        driver.quit();
    }
}
