package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class SignInOutTest extends BaseTest {

    @Test
    public void testSignOutByClickUserMenu() {

        Actions actions = new Actions(getDriver());

        actions.moveToElement(getDriver().findElement(By.id("root-action-UserAction"))).perform();
        getDriver().findElement(By.cssSelector(".jenkins-dropdown__item:last-child")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Sign in to Jenkins");
    }

    @Test
    public void testGoToProfileAfterSignOut() {

        String urlProfile = getDriver().getCurrentUrl() + "user/admin/";

        testSignOutByClickUserMenu();
        getDriver().get(urlProfile);

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Sign in to Jenkins");
    }
}
