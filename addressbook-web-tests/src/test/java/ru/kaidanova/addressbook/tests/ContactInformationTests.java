package ru.kaidanova.addressbook.tests;


import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInformationTests extends TestBase{
    @Test
    public void testContactInfo() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFronEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFronEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFronEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFronEditForm.getAddress()));

    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    private String mergePhones(ContactData contact) {
      return  Arrays.asList(contact.getHomePhone(), contact.getMobile(), contact.getWorkPhone())
              .stream().filter((s) -> ! s.equals(""))
              .map(ContactInformationTests::cleaned)
              .collect(Collectors.joining("\n"));

    }

    private String mergeEmails(ContactData contact) {
        return  Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
              //  .map(ContactInformationTests::cleaned)
                .collect(Collectors.joining("\n"));

    }


}
