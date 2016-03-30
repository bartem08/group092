package com.interview.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit test for Group class
 *
 * @author Yegor Gulimov
 */


public class GroupTest {

    @Test
    public void givenWhenGroupCreateWithNoArgConstructorThenGroupCreated() {
        Group group = new Group();

        assertNotNull(group);
        assertNull(group.getId());
        assertEquals(group.getCandidates().size(), 0);
        assertNull(group.getName());
    }

    @Test
    public void givenWhenGroupCreateWithOneArgConstructorThenGroupCreated() {
        Group group = new Group("Test");

        assertNotNull(group);
        assertNull(group.getId());
        assertEquals(group.getCandidates().size(), 0);
        assertEquals(group.getName(), "Test");
    }

    @Test
    public void givenWhenToStringCallThenGroupNameReturned() {
        Group group = new Group("Test");

        assertEquals(group.toString(), "Test");
    }


}