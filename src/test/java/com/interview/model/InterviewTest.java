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
        questionSet.add(new InterviewQuestion(new Question("A", 10), 9, false, false));
        interview = new Interview(questionSet, new Interviewer(), new ArrayList<>());
        empty = new Interview();
    }

    @Test
    public void initializedInterview() {
        assertNull(empty.getId());
        assertNull(empty.getComments());
        assertNull(empty.getInterviewer());
        assertNull(empty.getQuestions());
        assertEquals(empty.getResult(), 0.0);

        assertNull(interview.getId());
        assertNotNull(interview.getInterviewer());
        assertNotNull(interview.getComments());
        assertEquals(interview.getQuestions().size(), 1);
        assertEquals(interview.getResult(), 90.0);
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
    public void addCommentInitializeCommentList() {
        empty.addComment("Comment");
        assertNotNull(empty.getComments());
    }

    @Test
    public void notNullToString() {
        assertNotNull(interview.toString());
    }
}