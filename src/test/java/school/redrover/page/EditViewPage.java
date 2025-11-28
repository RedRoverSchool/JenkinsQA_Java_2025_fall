package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;
import java.util.List;

public class EditViewPage extends BasePage {

    @FindBy(xpath = "//button[@class='jenkins-dropdown__item ']")
    private List<WebElement> columnListForAdd;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public EditViewPage clickAddColumnDropDownButton() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add column']"))));

        new Actions(getDriver())
                .moveToElement(getWait2().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//button[text()='Add column']"))))
                .click()
                .perform();

        return this;
    }

    public List<String> getCurrentColumnList() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@class=contains(text(),'Columns')]")));

        return getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
                .xpath("//div[@class='repeated-chunk__header']")))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public EditViewPage addColumnInListView() {
        List<String> currentColumnList = getCurrentColumnList();

        Assert.assertNotEquals(columnListForAdd.size(), 0);
        for (WebElement element : columnListForAdd) {
            String columnName = element.getText().trim();

            if (!currentColumnList.contains(columnName)) {
                TestUtils.mouseEnterJS(getDriver(), element);
                TestUtils.clickJS(getDriver(), element);
            }
        }
        return this;
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