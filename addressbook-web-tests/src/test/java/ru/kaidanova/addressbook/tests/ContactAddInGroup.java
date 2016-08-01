package ru.kaidanova.addressbook.tests;

import org.testng.annotations.*;
import ru.kaidanova.addressbook.model.ContactData;
import ru.kaidanova.addressbook.model.GroupData;
import ru.kaidanova.addressbook.model.Groups;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddInGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withSecondName("test2")
                    .withAddress("test3").withMobile("test4").withWorkPhone("+7 (911) 25-35-55")
                    .withEmail1("test@test.ru").withEmail2("test@test.com"));
            app.goTo().homePage();
        }
    }

    @Test

    public void testContactAdditionInGroup() {

        ContactData contact = app.db().contacts().iterator().next();
        Groups availableGroups = app.db().availableGroupsForContact(contact);
        if (availableGroups.size() == 0) {
            Random rn = new Random();
            int extra = rn.nextInt(Integer.MAX_VALUE) + 1;
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("new_group_" + extra));
            availableGroups.add(app.db().groupByName("new_group_" + extra).iterator().next());
        }

        Groups contactGroupsBefore = contact.getGroups();
        GroupData group = availableGroups.iterator().next();
        app.goTo().homePage();
        app.contact().addToGroup(contact, group);
        ContactData contactAfter = app.db().contactByID(contact.getId()).iterator().next();
        Groups contactGroupsAfter = contactAfter.getGroups();
        assertThat(contactGroupsBefore.withAdded(group), equalTo(contactGroupsAfter));
    }


}
