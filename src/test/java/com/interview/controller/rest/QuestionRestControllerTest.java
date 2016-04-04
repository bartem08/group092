package com.interview.controller.rest;

import com.interview.Application;
import com.interview.model.Question;
import com.interview.service.QuestionService;
import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

/**
 * @author Anton Kruglikov.
 */
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class QuestionRestControllerTest extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    private QuestionService questionService;

    private String id;
    private Question question;

    private static final String QUESTIONS = "/rest/questions";
    private static final String QUESTION = "/rest/questions/{id}";
    private static final int NON_EXISTING_ID = 999;

    @BeforeClass
    public void initQuestion() {
        question = questionService.createQuestion(new Question());
        RestAssured.port = port;
    }

    @AfterClass
    public void tearDown() {
        questionService.deleteQuestion(question.getId());
    }

    @Test
    public void createdWhenSuccessQuestionCreating() {
        id = given()
                .contentType(JSON)
                .body(question)
            .when()
                .post(QUESTIONS)
            .then()
                .statusCode(SC_CREATED)
                .extract()
                .jsonPath()
                .getString("id").replaceAll("\\[", "").replaceAll("\\]", "");
    }

    @Test(dependsOnMethods = "createdWhenSuccessQuestionCreating")
    public void badRequestWhenCreateQuestionWithNoBody() {
        given()
            .contentType(JSON)
        .when()
            .post(QUESTIONS)
        .then()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenCreateQuestionWithNoBody")
    public void okWhenReadQuestion() {
        given()
            .contentType(JSON)
        .when()
            .get(QUESTION, id)
        .then()
            .statusCode(SC_OK)
            .body("id", notNullValue())
            .body("questionString", nullValue())
            .body("maxQuestionValue", is(0));
    }

    @Test(dependsOnMethods = "okWhenReadQuestion")
    public void okWhenReadAllQuestions() {
        given()
            .contentType(JSON)
        .when()
            .get(QUESTIONS)
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenReadAllQuestions")
    public void okWhenUpdateExistedQuestion() {
        question.setMaxQuestionValue((byte)3);
        given()
            .contentType(JSON)
            .body(question)
        .when()
            .put(QUESTION, id)
        .then()
            .statusCode(SC_OK)
            .extract()
            .jsonPath()
            .param("maxQuestionValue", hasItem(3));
    }

    @Test(dependsOnMethods = "okWhenUpdateExistedQuestion")
    public void badRequestWhenUpdateQuestionWithNoBody() {
        given()
            .contentType(JSON)
        .when()
            .put(QUESTION, id)
        .then()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenUpdateQuestionWithNoBody")
    public void noContentWhenUpdateNoExistedQuestion() {
        given()
            .contentType(JSON)
            .body(question)
        .when()
            .put(QUESTION,  NON_EXISTING_ID)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenUpdateNoExistedQuestion")
    public void okWhenDeleteExistedQuestion() {
        given()
            .contentType(JSON)
        .when()
            .delete(QUESTION, id)
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenDeleteExistedQuestion")
    public void noContentWhenReadNoExistedQuestion() {
        given()
            .contentType(JSON)
        .when()
            .get(QUESTION, id)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenReadNoExistedQuestion")
    public void noContentWhenDeleteNoExistedQuestion() {
        given()
            .contentType(JSON)
        .when()
            .delete(QUESTION, id)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

}