package ru.kaidanova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kaidanova.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withSecondName("test2").withGroup("")
                    .withAddress("test3").withMobile("test4").withWorkPhone("+7 (911) 25-35-55")
                    .withEmail1("test@test.ru").withEmail2("test@test.com"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDetails() {
        app.goTo().homePage();
        ContactData contact =  app.contact().all().iterator().next();
        ContactData contactDetails = app.contact().infoFromDetailsForm(contact);
        app.goTo().homePage();
        contact = app.contact().infoFromEditForm(contact);


        assertThat(contactDetails.getDetails(), equalToObject(mergeDetails(contact)));

    }


    private String mergeName(ContactData contact) {
        String mergedName
                = Arrays.asList(contact.getFirstname(), contact.getSecondname())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(" "));
        return mergedName;
    }

    private String mergePhones(ContactData contact) {
        String mergedPhone = "";
        if (!contact.getHomePhone().equals("")) mergedPhone = mergedPhone + "H: " + contact.getHomePhone() + "\n";
        if (!contact.getMobile().equals("")) mergedPhone = mergedPhone + "M: " + contact.getMobile() + "\n";
        if (!contact.getWorkPhone().equals("")) mergedPhone = mergedPhone + "W: " + contact.getWorkPhone() + "\n";
        return mergedPhone;
    }


    private String mergeEmails(ContactData contact) {
        String email1 = "";
        String email2 = "";
        String email3 = "";
        if (!contact.getEmail1().equals(""))
            email1 = contact.getEmail1() + " (www." + contact.getEmail1().split("@")[1] + ")";
        if (!contact.getEmail2().equals(""))
            email2 = contact.getEmail2() + " (www." + contact.getEmail2().split("@")[1] + ")";
        if (!contact.getEmail3().equals(""))
            email3 = contact.getEmail3() + " (www." + contact.getEmail3().split("@")[1] + ")";

        return Arrays.asList(email1, email2, email3)
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));


    }

    private String mergeDetails(ContactData contact) {
        return mergeName(contact) + "\n" + contact.getAddress() + "\n\n" + mergePhones(contact) + "\n" + mergeEmails(contact);

    }


}
