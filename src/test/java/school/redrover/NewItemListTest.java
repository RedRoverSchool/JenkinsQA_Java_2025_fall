package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class NewItemListTest extends BaseTest {
    @Test
    public void testNewItemTypeList(){
        List<String> expectedItemTypes = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        List<WebElement> elements = getDriver().findElements(By.cssSelector(".category>ul>li>div>label"));
        for (int i = 0; i < expectedItemTypes.size(); i++) {
            Assert.assertEquals(
                    elements.get(i).getText(),
                    expectedItemTypes.get(i)
            );
        }
    }
}
