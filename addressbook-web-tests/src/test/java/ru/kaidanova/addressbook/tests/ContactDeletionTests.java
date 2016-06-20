package ru.kaidanova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;

import java.util.List;


public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test5", "test6", "test1"));
            app.getNavigationHelper().gotoHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactDeletion();
        app.getContactHelper().submitContactDeletion();
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(before.size() - 1);

        Assert.assertEquals(after, before);

    }

}
