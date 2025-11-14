package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class PipelinePage extends BasePage {

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationPipelinePage clickConfigureInSideMenu(String newPipelineName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath(".//a[@href='/job/%s/configure']".formatted(newPipelineName))))
                .click();

        return new ConfigurationPipelinePage(getDriver());
    }
}
