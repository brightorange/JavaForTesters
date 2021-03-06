package ru.kaidanova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.Contacts;
import ru.kaidanova.addressbook.model.GroupData;
import ru.kaidanova.addressbook.model.Groups;

import java.util.List;


public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getSecondname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail1());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
       if (contactData.getPhoto() != null) {
        attach(By.name("photo"), contactData.getPhoto());}

        if (creation) {
            if ( contactData.getGroups().size() >0 ) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
       }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactDeletion() {
        click(By.xpath("//div/div[4]/form[2]/div[2]/input"));
    }


    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
        WebElement myDynamicElement = (new WebDriverWait(wd, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("maintable")));

    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void create(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        contactCache = null;
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    private void viewDetails(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
    }


    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDeletion();
        submitContactDeletion();
        contactCache = null;
    }


    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        contactCache = null;
        submitContactModification();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return contactCache;
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String secondName = cells.get(1).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            String address = cells.get(3).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withSecondName(secondName)
                    .withAllPhones(allPhones).withAddress(address).withAllEmails(allEmails));
        }
        return contactCache;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        contact.withMobile(wd.findElement(By.name("mobile")).getAttribute("value"))
                .withHomePhone(wd.findElement(By.name("home")).getAttribute("value"))
                .withWorkPhone(wd.findElement(By.name("work")).getAttribute("value"))
                .withAddress(wd.findElement(By.name("address")).getText())
                .withEmail1(wd.findElement(By.name("email")).getAttribute("value"))
                .withEmail2(wd.findElement(By.name("email2")).getAttribute("value"))
                .withEmail3(wd.findElement(By.name("email3")).getAttribute("value"))
                .withFirstName(wd.findElement(By.name("firstname")).getAttribute("value"))
                .withSecondName(wd.findElement(By.name("lastname")).getAttribute("value"));

        return contact;
    }


    public ContactData infoFromDetailsForm(ContactData contact) {
        selectContactById(contact.getId());
        viewDetails(contact.getId());
        String content = wd.findElement(By.id("content")).getText();
        contact.withDetails(content);
        return contact;

    }

    public void addToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        click(By.name("add"));

    }
}