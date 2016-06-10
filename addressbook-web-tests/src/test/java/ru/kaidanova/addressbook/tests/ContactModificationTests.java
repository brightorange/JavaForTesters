package ru.kaidanova.addressbook.tests;

import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact();
            app.getNavigationHelper().gotoHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test5", "test6", null), false);
        app.getContactHelper().submitContactModification();

    }
}
