package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;

public class RenameFolderTest extends BaseTest{


    String name = "MyFolder";
    String name2 = "ChangeNameFolder";

        @Test
        public void createFolderTest(){

            HomePage homePage = new HomePage(getDriver());
            homePage.clickNewItemOnLeftMenu();
            NewItemPage newItemPage = new NewItemPage(getDriver());
            newItemPage.sendName(name).selectFolderAndSubmit();
            ConfigurationFolderPage configurationFolderPage =  new ConfigurationFolderPage(getDriver());
            configurationFolderPage.clickSave();

            Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), name,
                    "Неверное название папки");
        }

        @Test (dependsOnMethods = "createFolderTest")
        public void testRenameFolder(){

            HomePage homePage = new HomePage(getDriver());
            homePage.openDropdownMenu(name).clickRenameItemInDropdownMenu();
            RenameFolderPage renameFolder = new RenameFolderPage(getDriver());
            renameFolder.clearName().sendNewName(name2).renameButtonClick();
            FolderPage folderPage = new FolderPage(getDriver());

            Assert.assertEquals(folderPage.getNameFolder(), name2);
        }
}




