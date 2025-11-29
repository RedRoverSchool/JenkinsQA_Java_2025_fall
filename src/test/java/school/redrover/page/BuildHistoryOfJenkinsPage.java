package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import school.redrover.common.BasePage;

import java.util.List;

public class BuildHistoryOfJenkinsPage extends BasePage {

    public BuildHistoryOfJenkinsPage(WebDriver driver) {
        super(driver);
    }

//    public RestApiPage clickRestApiLink() {
//        getDriver().findElement(By.xpath("//a[@href='api/']")).click();
//
//        return new RestApiPage(getDriver());
//    }

    public boolean emptyTableIsDisplayed() {
        return getDriver().findElement(By.xpath("//*[@id='projectStatus']")).isDisplayed();
    }

    public String getHeadingText() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public List<String> getTableHeadersText() {
        return getDriver()
                .findElements(By.cssSelector("thead th a.sortheader"))
                .stream()
                .map(webElement -> webElement.getText()
                        .replaceAll("[↓↑\\s\\u00A0]+$", "")
                        .trim())
                .toList();
    }
}
