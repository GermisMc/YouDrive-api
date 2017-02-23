package by.youdrive.commons;

import by.youdrive.commons.support.ResourceRepresentation;
import by.youdrive.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User extends ResourceRepresentation {
    private String id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private UserEntity.Contacts contacts;
    private String locale;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity.Contacts getContacts() {
        return contacts;
    }

    public String getLocale() {
        return locale;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContacts(UserEntity.Contacts contacts) {
        this.contacts = contacts;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
