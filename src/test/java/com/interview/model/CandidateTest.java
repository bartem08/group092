package com.interview.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.GregorianCalendar;

import static org.testng.Assert.*;

public class CandidateTest {
    @Test
     public void whenGivenCandidateWithEmptyConstructorThenCandidateCreatedExpected(){
        Candidate candidate = new Candidate();

        assertNull(candidate.getId());
        assertNull(candidate.getLastName());
        assertNull(candidate.getFirstName());
        assertNull(candidate.getDate());
        assertNull(candidate.getEnglishLevel());
        assertNull(candidate.getEmail());
        assertNull(candidate.getPhoneNumber());
        assertNull(candidate.getSkype());
    }

    @Test
    public void whenGivenCandidateWithConstructorWithThreeArgumentsThenCandidateCreatedExpected(){
        Candidate candidate = new Candidate("Belaya","Anna",new GregorianCalendar(2016,7,16,16,30));

        assertNull(candidate.getId());
        assertNotNull(candidate.getLastName());
        assertNotNull(candidate.getFirstName());
        assertNotNull(candidate.getDate());
    }

    @Test
    public void whenGivenCandidateWithConstructorWithSevenThenCandidateCreatedExpected(){
        Candidate candidate = new Candidate("Belaya","Anna",new GregorianCalendar(2016,7,16,16,30),Candidate.EnglishLevel.INTERMEDIATE,
                "050487****","aniikabelu@gmail.com","ann.belaya");

        assertNull(candidate.getId());
        assertNotNull(candidate.getLastName());
        assertNotNull(candidate.getFirstName());
        assertNotNull(candidate.getDate());
        assertNotNull(candidate.getEnglishLevel());
        assertNotNull(candidate.getEmail());
        assertNotNull(candidate.getPhoneNumber());
        assertNotNull(candidate.getSkype());
    }

    @Test
    public void givenCandidateToStringThenLastNameAndFirstNameOfCandidateExpected(){
        Candidate candidate = new Candidate("Belaya","Anna", new GregorianCalendar(2016,7,16,16,30));

        assertEquals(candidate.toString(),"LastName : " + candidate.getLastName() + ", FirstName : " + candidate.getFirstName());
    }
}