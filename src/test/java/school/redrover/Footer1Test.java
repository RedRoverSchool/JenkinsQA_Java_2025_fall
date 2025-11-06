package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class Footer1Test extends BaseTest {

    @Test
    public void testJenkinsVersion() {
        final String versionJenkins = "Jenkins 2.516.3";
        String actualVersionJenkins = getDriver().
                findElement(By.cssSelector(".page-footer__links > :nth-child(2)")).getText();

        Assert.assertEquals(actualVersionJenkins, versionJenkins);
    }

    @Test
    public void testJenkinsDropdown() {
        List<String> expectedDropdownItems = List.of(
                "About Jenkins",
                "Get involved",
                "Website"
        );

        getDriver().findElement(By.cssSelector("button.jenkins_ver")).click();

        List<WebElement> dropdownItems = new WebDriverWait(getDriver(), Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class,'jenkins-dropdown__item')]")
                ));

        List<String> actualDropdownItems = dropdownItems
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualDropdownItems, expectedDropdownItems);
    }
}
