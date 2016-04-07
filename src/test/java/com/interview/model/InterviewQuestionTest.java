package com.interview.model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Anton Kruglikov.
 */
public class InterviewQuestionTest {

    private InterviewQuestion emptyInterviewQuestion = new InterviewQuestion();

    @Test
    public void createInterviewQuestionWithEmptyConstructor() {
        assertNotNull(emptyInterviewQuestion);
        assertNull(emptyInterviewQuestion.getQuestion());
        assertNotNull(emptyInterviewQuestion.getFinalQuestionValue());
        assertEquals(emptyInterviewQuestion.isSkipped(), false);
    }

    @Test
    public void notNullToString() {
        assertNotNull(emptyInterviewQuestion.toString());
    }

}