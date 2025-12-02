package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.common.BasePage;

import java.util.List;

public class BuildHistoryOfJenkinsPage extends BasePage {

    @FindBy(xpath = "//table[@id='projectStatus']/tbody/tr")
    private List<WebElement> buildHistoryTableRows;

    @FindBy(css = "thead th a.sortheader")
    private List<WebElement> tableHeaders;

    public BuildHistoryOfJenkinsPage(WebDriver driver) {
        super(driver);
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
