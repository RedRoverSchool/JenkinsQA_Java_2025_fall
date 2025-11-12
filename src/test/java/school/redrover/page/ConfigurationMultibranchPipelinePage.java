package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class ConfigurationMultibranchPipelinePage extends BasePage {

    public ConfigurationMultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineProjectPage submitForm() {
        getDriver().findElement(By.tagName("form")).submit();

        return new MultibranchPipelineProjectPage(getDriver());
    }
}
