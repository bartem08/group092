package com.interview.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author NSS
 */
@Document(collection = "interviewers")
@TypeAlias("Interviewer")
public class Interviewer extends AbstractDocument implements Serializable {

    private String lastName;

    private String firstName;

    private String email;

    private String skype;

    private String phone;

    private String login;

    private String password;

    private String role;

    public Interviewer() {}

    @PersistenceConstructor
    public Interviewer(String lastName, String firstName, String email, String skype, String phone) {
        setLastName(lastName);
        setFirstName(firstName);
        setEmail(email);
        setSkype(skype);
        setPhone(phone);
    }

    public Interviewer(String lastName, String firstName, String email, String skype, String phone,
                       String login, String password, String role) {
        this(lastName, firstName, email, skype, phone);
        setLogin(login);
        setPassword(password);
        setRole(role);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Interviewer that = (Interviewer) object;

        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null) {
            return false;
        }
        return getFirstName() != null ? getFirstName().equals(that.getFirstName()) : that.getFirstName() == null;

    }

    @Override
    public int hashCode() {
        int result = getLastName() != null ? getLastName().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
