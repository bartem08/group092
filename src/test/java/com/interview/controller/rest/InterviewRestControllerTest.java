package com.interview.controller.rest;

import com.interview.config.MvcConfigurer;
import com.interview.model.Interview;
import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Artem Baranovskiy
 */
@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class InterviewRestControllerTest extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    private String id;

    private Interview interview;

    @BeforeClass
    public void initInterview() {
        interview = new Interview();
        RestAssured.port = port;
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
    public void okWhenReadInterview() {
        given()
                .contentType(JSON)
        .when()
                .get("/rest/interviews/" + id)
        .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .body("interviewer", nullValue())
                .body("questions", nullValue())
                .body("maxValue", is(0f))
                .body("finalValue", is(0f))
                .body("questions", nullValue());
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
        interview.addComment("CommentFromTest");
        given()
                .contentType(JSON)
                .body(interview)
        .when()
                .put("/rest/interviews/" + id)
        .then()
                .statusCode(SC_OK)
                .extract()
                .jsonPath()
                .param("comments", hasItem("CommentFromTest"));
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