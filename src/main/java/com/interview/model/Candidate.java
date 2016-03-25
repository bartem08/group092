package com.interview.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Document(collection = "candidates")
@TypeAlias("Candidate")
public class Candidate implements Serializable {

    @Id
    private String id;

    private String surname;

    private String name;

    private String patronymic;

    private List<Interview> interviewList;

    private Calendar date;

    private EnglishLevel englishLevel;

    private String phoneNumber;

    private String email;

    private String skype;

    public Candidate() {
        interviewList = new ArrayList<>();
    }

    public Candidate(String surname, String name, String patronymic,
                     List<Interview> interviewList, Calendar date, EnglishLevel englishLevel,
                     String phoneNumber, String email, String skype) {

        setName(name);
        setSurname(surname);
        setPatronymic(patronymic);
        setDate(date);
        setInterviewList(interviewList);
        setEnglishLevel(englishLevel);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setSkype(skype);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setInterviewList(List<Interview> interviewList) {
        this.interviewList = interviewList;
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
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", englishLevel='" + englishLevel + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public List<Interview> getInterviewList() {
        return interviewList;
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
        JAVA, PYTON;
    }

}
