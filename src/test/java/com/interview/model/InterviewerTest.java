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
        test.setFirstName("FirstName");
        test.setLastName("LastName");
        assertEquals(full, test);
        assertEquals(empty, new Interviewer());
    }

    @Test
    public void twoSameObjectsReturnSameHashCode() {
        final Interviewer test = new Interviewer();
        test.setFirstName("FirstName");
        test.setLastName("LastName");
        assertEquals(full.hashCode(), test.hashCode());
        assertEquals(empty.hashCode(), new Interviewer().hashCode());
    }

    @Test
    public void notNullToString() {
        assertNotNull(full.toString());
        assertNotNull(empty.toString());
    }
}