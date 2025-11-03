package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Dashboard2Test extends BaseTest {

    @Test
    public void testWelcomeMessageAndParagraghText() {
        String welcomeMessage = getDriver().findElement(By.xpath("//div[@id='view-message']/following::h1"))
                .getText();
        String paragraghText = getDriver().findElement(By.xpath("//div[@id='view-message']/following::p"))
                .getText();

        Assert.assertEquals(welcomeMessage, "Welcome to Jenkins!");
        Assert.assertEquals(paragraghText, "This page is where your Jenkins jobs will be displayed. To get started, " +
                "you can set up distributed builds or start building a software project.");
    }
}
