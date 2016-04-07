package com.interview.controller.rest;

import com.interview.model.Candidate;
import com.interview.model.Interview;
import com.interview.model.Interviewer;
import com.interview.service.CandidateService;
import com.interview.service.InterviewerService;
import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Artem Baranovskiy
 */

public class InterviewRestControllerTest extends RestIntegrationBase {

    private String id;

    private Interview interview;

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private CandidateService candidateService;

    private Interviewer interviewer;

    private Candidate candidate;

    @BeforeClass
    public void initInterview() {
        interviewer = interviewerService.createInterviewer(new Interviewer("A", "B", "C", "D", "E"));
        candidate = candidateService.createCandidate(new Candidate("A", "B", Calendar.getInstance()));
        interview = new Interview(interviewer, candidate);
        RestAssured.port = port;
    }

    @AfterClass
    public void tearDown() {
        interviewerService.deleteInterviewer(interviewer.getId());
        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void createdWhenSuccessInterviewCreating() {
        id = given()
                .contentType(JSON)
                .body(interview)
        .when()
                .post("/rest/interviews")
        .then()
                .statusCode(SC_CREATED)
                .extract()
                .jsonPath()
                .getString("id").replaceAll("\\[",  "").replaceAll("\\]", "");
    }

    @Test(dependsOnMethods = "createdWhenSuccessInterviewCreating")
    public void badRequestWhenCreateInterviewWithNoBody() {
        given()
                .contentType(JSON)
        .when()
                .post("/rest/interviews")
        .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenCreateInterviewWithNoBody")
    public void badRequestWhenCreateInterviewWithNonexistentInterviewer() {
        final Interview interview = new Interview();
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .post("/rest/interviews")
        .then()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .content(equalTo("Id does not exist in database"));
    }

    @Test(dependsOnMethods = "badRequestWhenCreateInterviewWithNonexistentInterviewer")
    public void okWhenReadInterview() {
        given()
                .contentType(JSON)
        .when()
                .get("/rest/interviews/" + id)
        .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .body("interviewer", notNullValue())
                .body("candidate", notNullValue())
                .body("questions", nullValue())
                .body("comments", nullValue())
                .body("result", is(0f));
    }

    @Test(dependsOnMethods = "okWhenReadInterview")
    public void okWhenReadAllInterviews() {
        given()
                .contentType(JSON)
        .when()
                .get("/rest/interviews")
        .then()
                .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenReadAllInterviews")
    public void okWhenUpdateExistedInterview() {
        interview.setComments("CommentFromTest");
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .put("/rest/interviews/" + id)
        .then()
                .statusCode(SC_OK)
                .extract()
                .jsonPath()
                .param("comments", is("CommentFromTest"));
    }

    @Test(dependsOnMethods = "okWhenUpdateExistedInterview")
    public void badRequestWhenUpdateInterviewWithNoBody() {
        given()
                .contentType(JSON)
        .when()
                .put("/rest/interviews/" + id)
        .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "badRequestWhenUpdateInterviewWithNoBody")
    public void noContentWhenUpdateNonexistentInterview() {
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .put("/rest/interviews/001100")
        .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenUpdateNonexistentInterview")
    public void badRequestWhenUpdateInterviewOnInterviewWithNonexistentInterviewer() {
        final Interviewer nonexistent = new Interviewer("A", "B", "C", "D", "E");
        nonexistent.setId("1111");
        interview.setInterviewer(nonexistent);
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .put("/rest/interviews/" + id)
        .then()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .content(equalTo("Id does not exist in database"));
    }

    @Test(dependsOnMethods = "badRequestWhenUpdateInterviewOnInterviewWithNonexistentInterviewer")
    public void badRequestWhenUpdateInterviewOnInterviewWithNonexistentCandidate() {
        final Candidate nonexistent = new Candidate("V", "V", Calendar.getInstance());
        nonexistent.setId("0000");
        interview.setCandidate(nonexistent);
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .put("/rest/interviews/" + id)
        .then()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .content(equalTo("Id does not exist in database"));
    }

    @Test(dependsOnMethods = "badRequestWhenUpdateInterviewOnInterviewWithNonexistentCandidate")
    public void okWhenDeleteExistedInterview() {
        given()
                .contentType(JSON)
        .when()
                .delete("/rest/interviews/" + id)
        .then()
                .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = "okWhenDeleteExistedInterview")
    public void noContentWhenReadNonexistentInterview() {
        given()
                .contentType(JSON)
        .when()
                .get("/rest/interviews/" + id)
        .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test(dependsOnMethods = "noContentWhenReadNonexistentInterview")
    public void noContentWhenDeleteNonExistentInterview() {
        given()
                .contentType(JSON)
        .when()
                .delete("/rest/interviews/" + id)
        .then()
                .statusCode(SC_NO_CONTENT);
    }

}
