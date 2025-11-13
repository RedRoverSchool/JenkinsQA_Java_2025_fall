package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineJobPage extends BasePage {

    public MultibranchPipelineJobPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='view-message']/../h1")))
                .getText();
    }

    public ConfigurationMultibranchPipelinePage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        return new ConfigurationMultibranchPipelinePage(getDriver());
    }
}
