package com.interview.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InterviewerTest {

    private Interviewer full;
    private Interviewer empty;

    @BeforeMethod
    public void initialize() {
        full = new Interviewer("LastName", "FirstName", "mail@mail", "skype111", "111");
        empty = new Interviewer();
    }

    @Test
    public void equalitySpecifiedFields() {
        assertEquals("LastName", full.getLastName());
        assertEquals("FirstName", full.getFirstName());
        assertEquals("mail@mail", full.getEmail());
        assertEquals("skype111", full.getSkype());
        assertEquals("111", full.getPhone());
    }

    @Test
    public void twoSameObjectsEqualsEachOther() {
        final Interviewer test = new Interviewer();
        test.setLastName("LastName");
        test.setFirstName("FirstName");
        test.setEmail("mail@mail");
        test.setSkype("skype111");
        test.setPhone("111");
        assertEquals(full, test);
    }

    @Test
    public void twoSameObjectsReturnSameHashCode() {
        final Interviewer test = new Interviewer();
        test.setLastName("LastName");
        test.setFirstName("FirstName");
        test.setEmail("mail@mail");
        test.setSkype("skype111");
        test.setPhone("111");
        assertEquals(full.hashCode(), test.hashCode());
    }

    @Test
    public void notNullToString() {
        assertNotNull(full.toString());
        assertNotNull(empty.toString());
    }
}