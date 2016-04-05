package com.interview.model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author Artem Baranovskiy
 */
public class InterviewTest {

    private Interview interview;

    private Interview empty;

    @BeforeMethod
    public void setUp() {
        Set<InterviewQuestion> questionSet = new HashSet<>();
        questionSet.add(new InterviewQuestion(new Question("A", (byte) 10), 9, false));
        interview = new Interview(questionSet, new Interviewer(), new Candidate(), null);
        empty = new Interview();
    }

    @Test
    public void initializedInterview() {
        assertNull(empty.getId());
        assertNull(empty.getComments());
        assertNull(empty.getInterviewer());
        assertNull(empty.getCandidate());
        assertNull(empty.getQuestions());
        assertEquals(empty.getResult(), 0f);

        assertNull(interview.getId());
        assertNotNull(interview.getInterviewer());
        assertNotNull(interview.getCandidate());
        assertNull(interview.getComments());
        assertEquals(interview.getQuestions().size(), 1);
        assertEquals(interview.getResult(), 90f);
    }

    @Test
    public void twoSameInterviewEqualsEachOther() {
        assertEquals(empty, new Interview());
    }

    @Test
    public void twoSameInterviewReturnsSameHashCode() {
        assertEquals(empty.hashCode(), new Interview().hashCode());
    }

    @Test
    public void notNullToString() {
        assertNotNull(interview.toString());
    }
}