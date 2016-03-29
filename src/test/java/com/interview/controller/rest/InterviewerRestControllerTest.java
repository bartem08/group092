package com.interview.controller.rest;

import com.interview.config.MvcConfigurer;
import com.interview.model.Interviewer;
import com.interview.service.InterviewerService;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class InterviewerRestControllerTest extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    private InterviewerService interviewerService;

    Interviewer first;
    Interviewer second;
    Interviewer forRemove;
    Interviewer toAdd;

    @BeforeClass
    public void setUp() {
        first = new Interviewer("FirstInterviewer", "LastName", "mail@mail", "skype111", "111");
        second = new Interviewer("SecondInterviewer", "LastNameSecond", "mail2@mail", "skype222", "222");
        forRemove = new Interviewer("ForRemoveInterviewer", "LastNameForRemove", "mail2@mail", "skype222", "222");
        toAdd = new Interviewer("ToAddInterviewer", "LastNameToAdd", "mail2@mail", "skype222", "222");

        first = interviewerService.createInterviewer(first);
        second = interviewerService.createInterviewer(second);
        forRemove = interviewerService.createInterviewer(forRemove);
        toAdd = interviewerService.createInterviewer(toAdd);

        RestAssured.port = port;
    }

    @AfterClass
    public void tearDown() {
        interviewerService.deleteInterviewer(first.getId());
        interviewerService.deleteInterviewer(second.getId());
        interviewerService.deleteInterviewer(toAdd.getId());
    }

    @Test
    public void canGetInterviewerById() {
        String firstId = first.getId();

        when()
                .get("/rest/interviewers/{id}", firstId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstName", Matchers.is("FirstInterviewer"))
                .body("id", Matchers.is(firstId));
    }

    @Test
    public void canGetAllInterviewers() {
        when()
                .get("/rest/interviewers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstName", Matchers.hasItems("FirstInterviewer", "SecondInterviewer"));
    }

    @Test
    public void canDeleteInterviewer() {
        when()
                .delete("/rest/interviewers/{id}", forRemove.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void canAddInterviewer() {
        given()
                .contentType(JSON)
                .body(toAdd)
                .when()
                .post("/rest/interviewers")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }
}