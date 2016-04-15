package com.interview.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.model.Candidate;
import com.interview.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.GregorianCalendar;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CandidateRestControllerTest extends RestIntegrationBase {
    private final String CANDIDATES = "/rest/candidates";

    @Autowired
    private CandidateService candidateService;

    @Test
    public void givenWhenCreateCandidateCallWithInvalidBodyThenStatusCodeBadRequest() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .body(" ")
                .when()
                .post(CANDIDATES)
                .then()
                .statusCode(SC_BAD_REQUEST);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenCreateCandidateCallWithoutBodyThenStatusCodeBadRequest() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .when()
                .post(CANDIDATES)
                .then()
                .statusCode(SC_BAD_REQUEST);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenReadAllCandidatesCallThenStatusCodeOk() throws JsonProcessingException {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));
        List<Candidate> candidates = candidateService.readAllCandidates();
        ObjectMapper mapper = new ObjectMapper();
        String candidatesJson = mapper.writeValueAsString(candidates);

        given()
                .contentType(JSON)
                .when()
                .get(CANDIDATES)
                .then()
                .statusCode(SC_OK)
                .body(is(candidatesJson));

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenReadCandidateWithExistedIdThenStatusCodeOk() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .when()
                .get(CANDIDATES + "/" + candidate.getId())
                .then()
                .statusCode(SC_OK)
                .body("id", is(candidate.getId()));

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenReadCandidateWithNotExistedIdThenStatusCodeNoContent() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .when()
                .get(CANDIDATES + "/wrongId")
                .then()
                .statusCode(SC_NO_CONTENT);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenUpdateCandidateWithValidIdThenStatusCodeOk() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));
        Candidate candidateUpdate = new Candidate("Bel", "Ann", new GregorianCalendar(2016, 7, 7));

        given()
                .contentType(JSON)
                .body(candidateUpdate)
                .when()
                .put(CANDIDATES + "/" + candidate.getId())
                .then()
                .statusCode(SC_OK);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenUpdateCandidateWithInvalidIdThenStatusCodeNoContent() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));
        Candidate candidateUpdate = new Candidate("Bel", "Ann", new GregorianCalendar(2016, 7, 7));

        given()
                .contentType(JSON)
                .body(candidateUpdate)
                .when()
                .put(CANDIDATES + "/wrongId")
                .then()
                .statusCode(SC_NO_CONTENT);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenDeleteCandidateWithExistedIdThenStatusCodeOk() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .when()
                .delete(CANDIDATES + "/" + candidate.getId())
                .then()
                .statusCode(SC_OK);

        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenDeleteCandidateWithInvalidIdThenStatusCodeNoContent() {
        Candidate candidate = candidateService.createCandidate(new Candidate("Belaya", "Anna", new GregorianCalendar(2016, 6, 6)));

        given()
                .contentType(JSON)
                .when()
                .delete(CANDIDATES + "/wrongId")
                .then()
                .statusCode(SC_NO_CONTENT);

        candidateService.deleteCandidate(candidate.getId());
    }
}