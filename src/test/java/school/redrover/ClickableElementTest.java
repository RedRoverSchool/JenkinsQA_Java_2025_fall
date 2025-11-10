package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ClickableElementTest extends BaseTest {

    @Test
    public void testClickableElement() throws InterruptedException {

        WebElement clickableButton = getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/footer/div/div[2]/button"));
        clickableButton.click();
        Thread.sleep(1000);
        String actualAriaExpanded = clickableButton.getAttribute("aria-expanded");
        Assert.assertEquals("true", actualAriaExpanded);
    }
}

