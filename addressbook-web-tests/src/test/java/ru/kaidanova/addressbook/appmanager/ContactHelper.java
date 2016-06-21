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

import java.util.ArrayList;
import java.util.HashSet;
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

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
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

    public void initContactModification() {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }

    public void submitContactModification() {
        click(By.name("update"));


    }

    public void create(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        //homePage();
    }


    public void delete(int index) {
        selectContact(index);
        initContactDeletion();
        submitContactDeletion();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDeletion();
        submitContactDeletion();
    }


    public void modify( ContactData contact) {
        selectContactById(contact.getId());
        initContactModification();
       fillContactForm(contact, false);
        submitContactModification();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }


    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            String firstName = element.findElement(By.xpath(".//td[3]")).getText();
            String secondName = element.findElement(By.xpath(".//td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withSecondName(secondName));
        }
        return contacts;
    }
}
