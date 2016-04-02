package com.interview.service.mongo;

import com.interview.config.MvcConfigurer;
import com.interview.model.Question;
import com.interview.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Anton Kruglikov.
 */

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class QuestionServiceImplTest extends AbstractTestNGSpringContextTests {

    Question question;

    @Autowired
    QuestionService questionService;

    @BeforeClass
    public void beforeEachTest() {
        question = questionService.createQuestion(new Question("Test Question", 12.6));
    }

    @AfterClass
    public void afterEachTest() {
        questionService.deleteQuestion(question.getId());
    }

    @Test
    public void createQuestionShouldAddQuestionToDB() {
        assertNotNull(question);
        assertEquals(question.getQuestionString(), "Test Question");
        assertEquals(question.getMaxQuestionValue(), 12.6);
    }

    @Test
    public void readExistingShouldGetQuestionFromDB() {
        Question readedQuestion = questionService.readQuestion(question.getId());

        assertEquals(question.getId(), readedQuestion.getId());
        assertEquals(question.getQuestionString(), readedQuestion.getQuestionString());
        assertEquals(question.getMaxQuestionValue(), readedQuestion.getMaxQuestionValue());
    }

    @Test
    public void deleteQuestionShouldReturnTrue() {
        Question question = questionService.createQuestion(new Question("For Deleting", 0.01));
        assertTrue(questionService.deleteQuestion(question.getId()));
        assertFalse(questionService.deleteQuestion(question.getId()));
    }

    @Test
    public void updateQuestionShouldPostInDBUpdatedQuestion() {
        Question oldQuestion = questionService.readQuestion(question.getId());
        question.setQuestionString("Updated Question");
        question.setMaxQuestionValue(25.0);
        Question updatedQuestion = questionService.updateQuestion(question);

        assertNotNull(updatedQuestion);
        assertEquals(updatedQuestion.getId(), oldQuestion.getId());
        assertNotEquals(updatedQuestion.getQuestionString(), oldQuestion.getQuestionString());
        assertNotEquals(updatedQuestion.getMaxQuestionValue(), oldQuestion.getQuestionString());
    }

}
