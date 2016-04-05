package com.interview.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.config.MvcConfigurer;
import com.interview.model.Candidate;
import com.interview.model.Group;
import com.interview.model.dto.GroupDTO;
import com.interview.service.CandidateService;
import com.interview.service.GroupService;
import com.jayway.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;


/**
 * Integration test for GroupRestController
 * using actual remote database
 *
 * @author Yegor Gulimov
 */

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class GroupRestControllerTest extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    @BeforeClass
    public void init() {
        Group group = groupService.readGroupByName("Test");
        if (group != null) {
            groupService.deleteGroup(group.getId());
        }
        RestAssured.port = port;
    }

    @Autowired
    private GroupService groupService;

    @Autowired
    private CandidateService candidateService;

    @Test
     public void givenWhenCreateGroupCallThenStatusCodeCreated() {
        String id =
                given()
                        .contentType(JSON)
                        .body(new Group("Test"))
                .when()
                        .post("/rest/groups")
                .then()
                        .statusCode(SC_CREATED)
                        .extract()
                        .jsonPath()
                        .getString("id").replaceAll("\\[",  "").replaceAll("\\]", "");

        assertTrue(groupService.deleteGroup(id));
    }

    @Test
    public void givenWhenCreateGroupCallWithInvalidBodyThenStatusCodeBadRequest() {
        given()
                .contentType(JSON)
                .body("invalid body")
        .when()
                .post("/rest/groups")
        .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void givenWhenCreateGroupCallWithoutBodyThenStatusCodeBadRequest() {
        given()
                .contentType(JSON)
        .when()
                .post("/rest/groups")
        .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void givenWhenCreateGroupCallWithDuplicateNameThenStatusCodeConflict() {

        Group group = groupService.createGroup(new Group("Test"));

        given()
                .contentType(JSON)
                .body(new Group("Test"))
        .when()
                .post("/rest/groups")
        .then()
                .statusCode(SC_CONFLICT);

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenReadAllGroupsCallThenStatusCodeOk() throws JsonProcessingException {
        //to ensure that at least one group existed in database
        Group group = groupService.createGroup(new Group("Test"));
        List<Group> groups = groupService.readAllGroups();
        ObjectMapper mapper = new ObjectMapper();
        String groupsJson = mapper.writeValueAsString(groups);

        given()
                .contentType(JSON)
        .when()
                .get("/rest/groups")
        .then()
                .statusCode(SC_OK)
                .body(is(groupsJson));

        groupService.deleteGroup(group.getId());
    }


    @Test
    public void givenWhenReadGroupCallWithExistedIdThenStatusCodeOk() {
        Group group = groupService.createGroup(new Group("Test"));

        given()
                .contentType(JSON)
        .when()
                .get("/rest/groups/" + group.getId())
        .then()
                .statusCode(SC_OK)
                .body("id", is(group.getId()))
                .body("name", is(group.getName()))
                .body("candidates", is(group.getCandidates()));

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenReadGroupCallWithNotExistedIdThenStatusCodeNoContent() {

        given()
                .contentType(JSON)
        .when()
                .get("/rest/groups/invalidId")
        .then()
                .statusCode(SC_NO_CONTENT);
    }


    @Test
    public void givenWhenUpdateGroupWithValidIdAndGroupThenStatusCodeOk() {

        Group createdGroup = groupService.createGroup(new Group("Test-1"));
        Group updatedGroup = new Group("Test-2");

        given()
                .contentType(JSON)
                .body(updatedGroup)
        .when()
                .put("/rest/groups/" + createdGroup.getId())
        .then()
                .statusCode(SC_OK)
                .body("id", is(createdGroup.getId()))
                .body("name", is(updatedGroup.getName()));

        assertTrue(groupService.deleteGroup(createdGroup.getId()));
    }

    @Test
    public void givenWhenUpdateGroupWithInvalidIdThenStatusCodeNoContent() {

        Group updatedGroup = new Group("Test");

        given()
                .contentType(JSON)
                .body(updatedGroup)
        .when()
                .put("/rest/groups/invalidId")
        .then()
                .statusCode(SC_NO_CONTENT);

    }

    @Test
    public void givenWhenUpdateGroupWithInvalidGroupThenStatusCodeBadRequest() {

        Group group = groupService.createGroup(new Group("Test"));

        given()
                .contentType(JSON)
                .body("invalid body")
        .when()
                .put("/rest/groups/" + group.getId())
        .then()
                .statusCode(SC_BAD_REQUEST)
                .body(notNullValue());  //error message

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenUpdateGroupWithExistedNameThenStatusCodeNoContent() {
        Group group1 = groupService.createGroup(new Group("Test-1"));
        Group group2 = groupService.createGroup(new Group("Test-2"));// Test-2 exists in database

        given()
                .contentType(JSON)
                .body(new Group("Test-2"))
        .when()
                .put("/rest/groups/" + group1.getId())
        .then()
                .statusCode(SC_NO_CONTENT);

        groupService.deleteGroup(group1.getId());
        groupService.deleteGroup(group2.getId());
    }

    @Test
    public void givenWhenUpdateGroupWithThisGroupNameThenStatusCodeOk() {
        Group group = groupService.createGroup(new Group("Test"));

        given()
                .contentType(JSON)
                .body(new Group("Test"))
        .when()
                .put("/rest/groups/" + group.getId())
        .then()
                .statusCode(SC_OK)
                .body("id", is(group.getId()))
                .body("name", is(group.getName()));

        assertTrue(groupService.deleteGroup(group.getId()));
    }

    @Test
    public void givenWhenDeleteGroupWithExistedIdThenStatusCodeOk() {
        Group group = groupService.createGroup(new Group("Test"));

        given()
                .contentType(JSON)
                .when()
                .delete("/rest/groups/" + group.getId())
        .then()
                .statusCode(SC_OK);

        assertNull(groupService.readGroup(group.getId()));
    }

    @Test
    public void givenWhenDeleteGroupWithInvalidIdThenStatusCodeNoContent() {

        given()
                .contentType(JSON)
        .when()
                .delete("/rest/groups/invalidId")
        .then()
                .statusCode(SC_NO_CONTENT);
    }

    //testing methods interacting with Group DTO

    @Test
    public void givenWhenReadAllGroupsDtoCallThenStatusCodeOk() throws JsonProcessingException {
        //to ensure that at least one group existed in database
        Group group = groupService.createGroup(new Group("Test"));

        ObjectMapper mapper = new ObjectMapper();

        GroupDTO groupDto = new GroupDTO(group);
        String groupDtoJson = mapper.writeValueAsString(groupDto);

        String receivedJson =
            given()
                .contentType(JSON)
            .when()
                .get("/rest/groups/dto")
            .then()
                .statusCode(SC_OK)
                .extract()
                .asString();

        assertTrue(receivedJson.contains(groupDtoJson));

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenReadGroupDtoCallThenStatusCodeOk() throws JsonProcessingException {
        //to ensure that at least one group existed in database
        Group group = groupService.createGroup(new Group("Test"));

        ObjectMapper mapper = new ObjectMapper();

        GroupDTO groupDto = new GroupDTO(group);
        String groupDtoJson = mapper.writeValueAsString(groupDto);

        String receivedJson =
                given()
                        .contentType(JSON)
                .when()
                        .get("/rest/groups/" + group.getId() + "/dto")
                .then()
                        .statusCode(SC_OK)
                        .extract()
                        .asString();

        assertEquals(receivedJson, groupDtoJson);

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenReadGroupDtoCallWithNotExistedIdThenStatusCodeNoContent() {

        given()
                .contentType(JSON)
        .when()
                .get("/rest/groups/invalidId/dto")
        .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test
    public void givenWhenAddCandidateToGroupCallWithExistedIdsThenStatusCodeOk() {
        Group group = groupService.createGroup(new Group("Test"));

        Candidate candidate = new Candidate();
        candidate.setFirstName("Test");
        candidate = candidateService.createCandidate(candidate);

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/addCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_OK);

        group = groupService.readGroup(group.getId());
        assertEquals(group.getCandidates().size(), 1);
        assertEquals(group.getCandidates().get(0).getId(), candidate.getId());
        assertEquals(group.getCandidates().get(0).getFirstName(), candidate.getFirstName());

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenAddCandidateToGroupCallWithDuplicateCandidateIdThenStatusCodeConflict() {
        Group group = groupService.createGroup(new Group("Test"));
        Candidate candidate = candidateService.createCandidate(new Candidate());

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/addCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_OK);

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/addCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_CONFLICT);

        group = groupService.readGroup(group.getId());
        assertEquals(group.getCandidates().size(), 1); //duplicate candidate doesn't added

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenAddCandidateToGroupCallWithWrongGroupOrCandidateIdThenStatusCodeBadRequest() {
        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/invalidId/addCandidate/invalidId")
        .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void givenWhenRemoveCandidateFromGroupCallWithExistedIdsThenStatusCodeOk() {
        Group group = groupService.createGroup(new Group("Test"));

        Candidate candidate = new Candidate();
        candidate.setFirstName("Test");
        candidate = candidateService.createCandidate(candidate);

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/addCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_OK);

        group = groupService.readGroup(group.getId());
        assertEquals(group.getCandidates().size(), 1);
        assertEquals(group.getCandidates().get(0).getId(), candidate.getId());
        assertEquals(group.getCandidates().get(0).getFirstName(), candidate.getFirstName());

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/removeCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_OK);

        group = groupService.readGroup(group.getId());
        assertTrue(group.getCandidates().isEmpty());

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenRemoveNotPresentCandidateFromGroupCallThenStatusCodeConflict() {
        Group group = groupService.createGroup(new Group("Test"));
        Candidate candidate = candidateService.createCandidate(new Candidate());

        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/" + group.getId() + "/removeCandidate/" + candidate.getId())
        .then()
                .statusCode(SC_CONFLICT);

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate.getId());
    }

    @Test
    public void givenWhenRemoveCandidateFromGroupCallWithNotExistedCandidateOrGroupIdThenStatusCodeBadRequest() {
        given()
                .contentType(JSON)
        .when()
                .put("/rest/groups/invalidId/removeCandidate/invalidId")
        .then()
                .statusCode(SC_BAD_REQUEST);
    }


}