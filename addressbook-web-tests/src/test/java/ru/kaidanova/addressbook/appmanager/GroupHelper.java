package ru.kaidanova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.GroupData;
import ru.kaidanova.addressbook.model.Groups;

import java.util.List;


public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    private void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteGroup() {
        click(By.name("delete"));
    }

    public void create(GroupData groupData) {
        initGroupCreation();
        fillGroupForm(groupData);
        submitGroupCreation();
        groupCache = null;
        returnToGroupPage();
    }


    public void modify(GroupData group) {
       selectGroupById(group.getId());
       initGroupModification();
        fillGroupForm(group);
       submitGroupModification();
        groupCache = null;
        returnToGroupPage();
    }


    public void delete(GroupData group) {
        selectGroupById(group.getId());
        deleteGroup();
        groupCache = null;
        returnToGroupPage();
    }


    public void goToSpecificGroupPage(GroupData group) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
        WebElement myDynamicElement = (new WebDriverWait(wd, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("maintable")));
    }


    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Groups groupCache = null;

    public Groups all() {
        if (groupCache != null) {
            return new Groups(groupCache);
        }
        groupCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groupCache.add(new GroupData().withId(id).withName(name));
        }
        return new Groups(groupCache);
    }


    public void removeContact(ContactData contact) {
        wd.findElement(By.cssSelector("input[value='" + contact.getId() + "']")).click();
        click(By.name("remove"));
    }
}
