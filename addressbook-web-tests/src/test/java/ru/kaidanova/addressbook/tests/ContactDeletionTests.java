package ru.kaidanova.addressbook.tests;

import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;


public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test5", "test6", "test1"));
            app.getNavigationHelper().gotoHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getContactHelper().submitContactDeletion();

    }

}
