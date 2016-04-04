package com.interview.model.dto;

import com.interview.model.Group;
import com.interview.model.Interviewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NSS on 31.03.2016.
 */
public class InterviewerDTO implements Serializable {

    private String id;

    private String lastName;

    private String firstName;

    private String email;

    private String skype;

    private String phone;

    private String login;

    public InterviewerDTO(Interviewer interviewer) {
        id = interviewer.getId();
        lastName = interviewer.getLastName();
        firstName = interviewer.getFirstName();
        email = interviewer.getEmail();
        skype = interviewer.getSkype();
        phone = interviewer.getPhone();
        login = interviewer.getLogin();
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
}
