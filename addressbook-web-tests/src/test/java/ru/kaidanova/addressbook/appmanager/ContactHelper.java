package ru.kaidanova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.kaidanova.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    private FirefoxDriver wd;
    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getSecondname());
        type(By.name("title"),contactData.getTitle());
        type(By.name("address"),contactData.getAddress());
        type(By.name("mobile"),contactData.getMobile());
      }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }
}
