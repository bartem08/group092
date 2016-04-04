package com.interview.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Document(collection = "candidates")
@TypeAlias("Candidate")
public class Candidate extends AbstractDocument implements Serializable {

    private String lastName;

    private String firstName;

    private Calendar date;

    private EnglishLevel englishLevel;

    private String phoneNumber;

    private String email;

    private String skype;

    public Candidate() {

    }

    public Candidate(String lastName, String firstName, Calendar date) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
    }

    public Candidate(String surname, String name, String patronymic,
                     List<Interview> interviewList, Calendar date, EnglishLevel englishLevel,
                     String phoneNumber, String email, String skype) {
        setFirstName(name);
        setLastName(surname);
        setDate(date);
        setEnglishLevel(englishLevel);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setSkype(skype);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        return id != null ? !id.equals(candidate.id) : candidate.id != null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", last name='" + lastName + '\'' +
                ", first name='" + firstName + '\'' +
                ", englishLevel='" + englishLevel + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Calendar getDate() {
        return date;
    }

    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSkype() {
        return skype;
    }

    public enum EnglishLevel {
        INTERMEDIATE, UPPERINTERMEDIATE;
    }

    public enum Group {
        JAVA, PYTHON;
    }

}
