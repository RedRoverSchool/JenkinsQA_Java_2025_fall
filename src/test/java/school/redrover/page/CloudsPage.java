package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class CloudsPage extends BasePage {

    public CloudsPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    public String getParagraphText() {
        return getDriver().findElement(By.xpath("//p")).getText();
    }
}
