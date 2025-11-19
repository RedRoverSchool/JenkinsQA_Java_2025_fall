package school.redrover.page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class SearchModalPage extends BaseModel {

    private final By inputField = By.id("command-bar");
    private final By searchResults = By.xpath("//div[@id='search-results']//a");

    public SearchModalPage(WebDriver driver) {
        super(driver);
    }

    public SearchModalPage searchFor(String jobName) {
        WebElement input = getWait2().until(ExpectedConditions.elementToBeClickable(inputField));
        input.sendKeys(jobName);
        return this;
    }

    public SearchModalPage searchFor(String jobName, String previousItemName) {
        WebElement input = getWait2().until(ExpectedConditions.elementToBeClickable(inputField));
        input.clear();
        input.sendKeys(jobName);

        if (previousItemName != null && !previousItemName.isEmpty()) {
            getWait5().until(driver ->
                    !driver.findElement(searchResults).getText().contains(previousItemName));
        }

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

    public List<String> getSearchResultsAndClose() {
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.className("jenkins-command-palette__results__heading")));
        List<String> searchResultsTexts = new ArrayList<>();
        for (WebElement element : getDriver().findElements(searchResults)) {
            searchResultsTexts.add(element.getText());
        }
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(inputField), 0, -50)
                .click()
                .perform();
        return searchResultsTexts;
    }

    public String clickSub() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search-results > a")));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("command-bar"))).sendKeys(Keys.ENTER);
        getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();

        return getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();
    }
}
