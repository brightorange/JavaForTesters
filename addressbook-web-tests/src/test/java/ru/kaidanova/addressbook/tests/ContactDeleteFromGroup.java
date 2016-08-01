package ru.kaidanova.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.Contacts;
import ru.kaidanova.addressbook.model.GroupData;
import ru.kaidanova.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactDeleteFromGroup extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactDeletionFromGroup() {
        GroupData group = app.db().groups().iterator().next();
        if (group.getContacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstName("test").withGroups(new Groups().withAdded(group)));

        }
        GroupData groupBefore = app.db().groupById(group.getId()).iterator().next();
        Contacts before = groupBefore.getContacts();
        app.goTo().homePage();
        app.group().goToSpecificGroupPage(group);
        ContactData contact = groupBefore.getContacts().iterator().next();
        app.group().removeContact(contact);
        GroupData groupAfter = app.db().groupById(group.getId()).iterator().next();
        Contacts after = groupAfter.getContacts();
        assertThat(after, equalTo(before.without(contact)));
    }
}
