package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.common.BasePage;

public class MovePage extends BasePage {

    public MovePage(WebDriver driver) {
        super(driver);
    }

    public MovePage selectDestinationFolder(String folderName) {
        Select selectObject = new Select(getDriver().findElement(By.className("jenkins-select__input")));
        selectObject.selectByVisibleText("Jenkins » %s".formatted(folderName));
        return this;
    }

    public void clickMoveButtonAndGoHome() {
        String urlBeforeMoving = getDriver().getCurrentUrl();
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeMoving)));
        gotoHomePage();
    }
}
