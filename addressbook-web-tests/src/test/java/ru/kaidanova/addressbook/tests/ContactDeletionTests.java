package ru.kaidanova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;


public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withSecondName("test2").withAddress("test3").withMobile("test4"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() {

        Contacts before = app.contact().all();
        ContactData deletedContact  = before.iterator().next();
        app.contact().delete(deletedContact);
        Contacts after = app.contact().all();

        assertThat(after.size(), equalTo(before.size() - 1));
        assertThat(after, equalTo(before.without(deletedContact)));

    }


}
