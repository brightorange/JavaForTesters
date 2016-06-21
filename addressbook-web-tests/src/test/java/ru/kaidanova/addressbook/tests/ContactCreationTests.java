package ru.kaidanova.addressbook.tests;


import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("test1").withSecondName("test2").withAddress("test3").withMobile("test4").withGroup("test1");
        app.contact().create(contact);
        app.goTo().homePage();
        Contacts after = app.contact().all();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }


}