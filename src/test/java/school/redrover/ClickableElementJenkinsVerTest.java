package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ClickableElementJenkinsVerTest extends BaseTest {

    @Test
    public void testClickableElement() throws InterruptedException {

        WebElement clickableButton = getDriver().findElement(By.xpath("//*[@class = 'jenkins-button jenkins-button--tertiary jenkins_ver']"));
        clickableButton.click();

        getWait2().until(driver -> "true".equals(clickableButton.getAttribute("aria-expanded")));
        String actualAriaExpanded = clickableButton.getAttribute("aria-expanded");
        Assert.assertEquals("true", actualAriaExpanded);
    }
}

