package ru.kaidanova.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String secondname;
    private final String title;
    private final String address;
    private final String mobile;

    public ContactData(String firstname, String secondname, String title, String address, String mobile) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.title = title;
        this.address = address;
        this.mobile = mobile;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }
}