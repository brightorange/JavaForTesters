package ru.kaidanova.addressbook.tests;

import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test5", "test6"));
        app.getContactHelper().submitContactModification();

    }
}
