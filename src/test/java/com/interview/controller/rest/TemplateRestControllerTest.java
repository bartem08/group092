package com.interview.controller.rest;

import com.interview.Application;
import com.interview.model.Interviewer;
import com.interview.model.Question;
import com.interview.model.Template;
import com.interview.service.InterviewerService;
import com.interview.service.QuestionService;
import com.interview.service.TemplateService;
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
import static org.hamcrest.Matchers.*;

/**
 * @author Anton Kruglikov.
 */
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class TemplateRestControllerTest extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    private TemplateService templateService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private InterviewerService interviewerService;

    private String templateId;
    private String templateId2;
    private Template template;

    private Question question;

    private Interviewer interviewer;

    private static final String TEMPLATES = "/rest/templates";
    private static final String TEMPLATE = "/rest/templates/{id}";
    private static final String TEMPLATE_QUESTIONS = "/rest/templates/{id}/questions";
    private static final String TEMPLATE_QUESTION = "/rest/templates/{templateId}/questions/{questionId}";
    private static final int NON_EXISTING_ID = 999;

    @BeforeClass
    public void initQuestion() {
        question = questionService.createQuestion(new Question("Do you have brain?", (byte)100));
        interviewer = interviewerService.createInterviewer(new Interviewer("Test", "Test", "Test", "Test", "Test"));
        template = new Template();
        template.addQuestion(question);
        template.setInterviewer(interviewer);
        templateService.createTemplate(template);
        RestAssured.port = port;
    }

    @AfterClass
    public void tearDown() {
        templateService.deleteTemplate(template.getId());
        templateService.deleteTemplate(templateId2);
        questionService.deleteQuestion(question.getId());
        interviewerService.deleteInterviewer(interviewer.getId());
    }

    @Test
    public void createdWhenSuccessTemplateCreating() {
        templateId = given()
            .contentType(JSON)
            .body(template)
        .when()
            .post(TEMPLATES)
        .then()
            .statusCode(SC_CREATED)
            .extract()
            .jsonPath()
            .getString("id").replaceAll("\\[", "").replaceAll("\\]", "");
    }

    @Test(dependsOnMethods = "createdWhenSuccessTemplateCreating")
    public void badRequestWhenCreateTemplateWithNoBody() {
        given()
            .contentType(JSON)
        .when()
            .post(TEMPLATES)
        .then()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenCreateTemplateWithNoBody")
    public void okWhenCreateTemplateWithNullQuestionList() {
        Template template = new Template();
        template.setInterviewer(interviewer);
        templateId2 = given()
                .contentType(JSON)
                .body(template)
        .when()
                .post(TEMPLATES)
        .then()
                .statusCode(SC_CREATED)
                .extract()
                .jsonPath()
                .getString("id").replaceAll("\\[", "").replaceAll("\\]", "");
    }

    @Test(dependsOnMethods = "okWhenCreateTemplateWithNullQuestionList")
    public void okWhenReadTemplate() {
        given()
            .contentType(JSON)
        .when()
            .get(TEMPLATE, templateId)
        .then()
            .statusCode(SC_OK)
            .body("id", notNullValue())
            .body("name", nullValue())
            .body("questions", notNullValue())
            .body("interviewer", notNullValue());
    }

    @Test(dependsOnMethods = "okWhenReadTemplate")
    public void okWhenReadAllTemplates() {
        given()
            .contentType(JSON)
        .when()
            .get(TEMPLATES)
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenReadAllTemplates")
    public void okWhenUpdateExistedTemplate() {
        template.setName("JAVA");
        given()
            .contentType(JSON)
            .body(template)
        .when()
            .put(TEMPLATE, templateId)
        .then()
            .statusCode(SC_OK)
            .extract()
            .jsonPath()
            .param("name", hasItem("JAVA"));
    }

    @Test(dependsOnMethods = "okWhenUpdateExistedTemplate")
    public void badRequestWhenUpdateTemplateWithNoBody() {
        given()
            .contentType(JSON)
        .when()
            .put(TEMPLATE, templateId)
        .then()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenUpdateTemplateWithNoBody")
    public void noContentWhenUpdateNoExistedTemplate() {
        given()
            .contentType(JSON)
            .body(template)
        .when()
            .put(TEMPLATE,  NON_EXISTING_ID)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenUpdateNoExistedTemplate")
    public void okWhenReadAllQuestionsFromTemplate() {
        given()
           .contentType(JSON)
        .when()
           .get("/rest/templates/" + templateId + "/questions")
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenReadAllQuestionsFromTemplate")
    public void okWhenDeleteQuestionFromTemplate() {
        given()
            .contentType(JSON)
        .when()
            .delete("/rest/templates/" + templateId + "/questions/" + question.getId())
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenDeleteQuestionFromTemplate")
    public void noContentWhenDeleteNoExistedQuestionFromTemplate() {
        given()
            .contentType(JSON)
        .when()
            .delete("/rest/templates/" + templateId + "/questions/" + question.getId())
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenDeleteNoExistedQuestionFromTemplate")
    public void noContentWhenGetAllQuestionsFromEmptyTemplate() {
        given()
            .contentType(JSON)
        .when()
            .get("/rest/templates/" + templateId + "/questions")
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenGetAllQuestionsFromEmptyTemplate")
    public void createdWhenSuccessAddingQuestionToTemplate() {
        given()
            .contentType(JSON)
            .body(question)
        .when()
            .post("/rest/templates/" + templateId + "/questions")
        .then()
            .statusCode(SC_CREATED)
            .extract()
            .jsonPath()
            .getString("id").replaceAll("\\[", "").replaceAll("\\]", "");
    }

    @Test(dependsOnMethods = "createdWhenSuccessAddingQuestionToTemplate")
    public void badRequestWhenAddQuestionToTemplateWithNoBody() {
        given()
            .contentType(JSON)
        .when()
            .post("/rest/templates/" + templateId + "/questions")
        .then()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenAddQuestionToTemplateWithNoBody")
    public void okWhenDeleteExistedTemplate() {
        given()
            .contentType(JSON)
        .when()
            .delete(TEMPLATE, templateId)
        .then()
            .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenDeleteExistedTemplate")
    public void noContentWhenReadNoExistedTemplate() {
        given()
            .contentType(JSON)
        .when()
            .get(TEMPLATE, templateId)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenReadNoExistedTemplate")
    public void noContentWhenDeleteNoExistedTemplate() {
        given()
            .contentType(JSON)
        .when()
            .delete(TEMPLATE, templateId)
        .then()
            .statusCode(SC_NO_CONTENT);
    }

}