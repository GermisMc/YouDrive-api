package by.youdrive.domain;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserEntity implements Principal {

    private ID id;
    private String firstName;
    private String lastName;
    private Contacts contacts;
    private boolean admin;
    private Locale locale;
    private String secret;
    private JSON customProperties;

    public UserEntity(ID id, String firstName, String lastName, Contacts contacts, boolean admin, Locale locale, String secret,
                      JSON customProperties) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contacts = contacts;
        this.admin = admin;
        this.locale = locale;
        this.secret = secret;
        this.customProperties = customProperties;
    }

    @Override
    public String getName() {
        return id.toString();
    }

    public ID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getSecret() {
        return secret;
    }

    public JSON getCustomProperties() {
        return customProperties;
    }

    public Contacts getContacts() {
        return contacts;
    }


    public static class Contacts {
        private List<String> emails;

        public Contacts() {}

        public List<String> getEmails() {
            if (emails == null) {
                emails = new ArrayList<>();
            }
            return emails;
        }

        public void setEmails(List<String> emails) {
            this.emails = emails;
        }
    }
}
