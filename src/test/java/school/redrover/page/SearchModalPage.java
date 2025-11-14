package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BaseModel;

public class SearchModalPage extends BaseModel {

    public SearchModalPage(WebDriver driver) {
        super(driver);
    }

    public SearchModalPage searchFor(String jobName) {
        WebElement input = getWait2().until(ExpectedConditions.elementToBeClickable(By.id("command-bar")));
        input.sendKeys(jobName);
        return this;
    }

    public boolean isNoResultsFound(String jobName) {
        try {
            getWait5().until(ExpectedConditions.textToBePresentInElementLocated(
                    By.id("search-results"), "No results for %s".formatted(jobName)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
