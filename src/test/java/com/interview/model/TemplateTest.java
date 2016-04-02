package com.interview.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Anton Kruglikov.
 */
public class TemplateTest {

    private Template emptyTemplate = new Template();
    private Template template = new Template();
    private Question question = new Question();

    @BeforeMethod
    public void setUp() {
        Question q = new Question();
        List<Question> questions = new ArrayList<>();
        questions.add(q);
        Interviewer interviewer = new Interviewer();
        template.setName("Test");
        template.setQuestions(questions);
        template.setFavourite(true);
        template.setInterviewer(interviewer);
    }

    @Test
    public void createTemplateWithEmptyConstructor() {
        assertNotNull(emptyTemplate);
        assertNull(emptyTemplate.getId());
        assertNull(emptyTemplate.getInterviewer());
        assertNull(emptyTemplate.getName());
    }

    @Test
    public void twoSameTemplatesEqualsEachOther() {
        assertEquals(emptyTemplate, new Template());
    }

    @Test
    public void twoTheSameTemplatesReturnsTheSameHashCode() {
        assertEquals(emptyTemplate.hashCode(), new Template().hashCode());
    }

    @Test
    public void addFirstQuestionToTemplateInitializeQuestionsList() {
        emptyTemplate.addQuestion(question);
        assertNotNull(emptyTemplate.getQuestions());
    }

    @Test
    public void notNullToString() {
        assertNotNull(template.toString());
    }

}