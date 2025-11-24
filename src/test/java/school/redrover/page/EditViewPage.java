package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;

public class EditViewPage extends BasePage {

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddColumnDropDownButton() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add column']"))));

        new Actions(getDriver())
                .moveToElement(getWait2().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//button[text()='Add column']"))))
                .click()
                .perform();
    }

    public List<WebElement> getAddColumnList() {
        return getDriver().findElements(By.xpath("//button[@class='jenkins-dropdown__item ']"));
    }

    public List<String> getCurrentColumnList() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=contains(text(),'Columns')]")));

        return getDriver().findElements(By.xpath("//div[@class='repeated-chunk__header']"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public EditViewPage selectJobCheckbox(String jobName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//label[text()='%s']".formatted(jobName))))
                .click();

        return this;
    }

    public void clickSubmitButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By
                  .xpath("//button[@name='Submit']"))).click();
    }

    public EditViewPage clickDeleteButton(String columnName) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath(".//div[contains(text(),'%s')]".formatted(columnName)))));

        WebElement deleteButton = getWait5().until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[contains(text(),'%s')]/button".formatted(columnName))));
        deleteButton.click();
        getWait5().until(ExpectedConditions.stalenessOf(deleteButton));

        return this;
    }
}