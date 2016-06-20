package ru.kaidanova.addressbook.model;

public class ContactData {
    private  int id;
    private final String firstname;
    private final String secondname;
    private final String title;
    private final String address;
    private final String mobile;
    private String group;

    public void setId(int id) {
        this.id = id;
    }

    public ContactData(int id, String firstname, String secondname, String title, String address, String mobile, String group) {
       this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.title = title;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }

    public ContactData(String firstname, String secondname, String title, String address, String mobile, String group) {
        this.id = Integer.MAX_VALUE;
        this.firstname = firstname;
        this.secondname = secondname;
        this.title = title;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return secondname != null ? secondname.equals(that.secondname) : that.secondname == null;

    }

    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                '}';
    }

    public int getId() {
        return id;
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

    public String getGroup() {
        return group;
    }
}
