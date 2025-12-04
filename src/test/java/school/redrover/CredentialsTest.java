package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CredentialsTest extends BaseTest {

    @Test
    public void testCreateUsernameWithPassword() {
        final String username = "Admin";
        final String password = "123321";
        final String description = "Test Create Username with password kind";
        final String expectedName = String.format("%s/****** (%s)", username, description);

        AtomicReference<List<WebElement>> credentialsListBeforeCreateNewOne = new AtomicReference<>();
        List<WebElement> credentialsListAfterCreateNewOne = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickCredentialsLink()
                .clickGlobalLink()
                .getGlobalCredentialsList(credentialsListBeforeCreateNewOne::set)
                .clickManageJenkinsGear()
                .clickCredentialsLink()
                .clickGlobalLink()
                .clickAddCredentialsButton()
                .enterUsername(username)
                .enterPassword(password)
                .enterDescription(description)
                .clickCreateButton()
                .getGlobalCredentialsList();

        boolean actualName = credentialsListAfterCreateNewOne
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .anyMatch(text -> text.equals(expectedName));

        Assert.assertEquals(
                credentialsListAfterCreateNewOne.size(),
                credentialsListBeforeCreateNewOne.get().size() + 1,
                "Количество credentials должно увеличиться на 1");

        Assert.assertTrue(
                actualName,
                String.format("Credential с именем '%s' не найден", expectedName));
    }
}
