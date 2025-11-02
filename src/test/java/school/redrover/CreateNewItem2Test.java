package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class CreateNewItem2Test extends BaseTest {

    @Test
    public void createNewItemTest() {
        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        Assert.assertEquals(getDriver().getTitle(), "New Item - Jenkins");
    }

    @Test
    public void checkInputFieldIsDiplayedTest() {
        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#name")).isDisplayed());
    }

    @Test
    public void checkListOfAvailableItemTypesTest() {
        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        List<String> expectedResult = List.of("Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder");
        List<String> actualResult = getDriver().findElements(By.cssSelector("label .label"))
                .stream().map(WebElement::getText).toList();
        Assert.assertEquals(actualResult, expectedResult);
    }
}
