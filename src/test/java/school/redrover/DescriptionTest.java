package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class DescriptionTest extends BaseTest {

    @Test
    public void testCreateDescription() throws InterruptedException {
        final String description = "Test description";

        setDescription(getDriver(), description);

        Thread.sleep(2000);

        String actualDescription = getContent(getDriver());
        Assert.assertEquals(actualDescription, description);
    }

    @Test
    void testChangeDescription() throws InterruptedException {
        final String firstDescription = "First text!";
        final String secondDescription = "Second text!";

        setDescription(getDriver(), firstDescription);

        Thread.sleep(2000);

        setDescription(getDriver(), secondDescription);

        String actualDescription = getContent(getDriver());
        Assert.assertEquals(actualDescription, secondDescription + firstDescription);
    }

    static void setDescription(WebDriver driver, String description) {
        driver.findElement(By.id("description-link")).click();
        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("Submit")).click();
    }

    static String getContent(WebDriver driver) {
        return driver.findElement(By.id("description-content")).getText();
    }
}
