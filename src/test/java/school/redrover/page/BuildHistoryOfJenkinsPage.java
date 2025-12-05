package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BuildHistoryOfJenkinsPage extends BaseSideMenuItemPage {

    @FindBy(xpath = "//table[@id='projectStatus']/tbody/tr")
    private List<WebElement> buildHistoryTableRows;

    @FindBy(css = "thead th a.sortheader")
    private List<WebElement> tableHeaders;

    public BuildHistoryOfJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
    }

    public boolean isBuildHistoryEmpty() {
        return buildHistoryTableRows.isEmpty();
    }

    public List<String> getTableHeadersText() {
        return tableHeaders
                .stream()
                .map(webElement -> webElement.getText()
                        .replaceAll("[↓↑\\s\\u00A0]+$", "")
                        .trim())
                .toList();
    }
}
