package com.interview.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NSS
 */
@Document(collection = "interviewers")
@TypeAlias("Interviewer")
public class Interviewer implements Serializable {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String mail;

    private List<Group> groups = new ArrayList();

    private List<Template> templates = new ArrayList();

    public Interviewer() {}

    @PersistenceConstructor
    public Interviewer(String firstName, String lastName, String mail) {
        setFirstName(firstName);
        setLastName(lastName);
        setMail(mail);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interviewer that = (Interviewer) o;

        return firstName != null ? firstName.equals(that.firstName) : that.firstName == null
                && (lastName != null ? lastName.equals(that.lastName) : that.lastName == null);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

}
