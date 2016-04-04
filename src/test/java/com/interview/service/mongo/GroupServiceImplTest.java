package com.interview.service.mongo;

import com.interview.config.MvcConfigurer;
import com.interview.model.Candidate;
import com.interview.model.Group;
import com.interview.service.CandidateService;
import com.interview.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.testng.Assert.*;


/**
 * Integration test for GroupServiceImpl
 * actual remote database used
 *
 * @author Yegor Gulimov
 */

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class GroupServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GroupService groupService;

    @Autowired
    private CandidateService candidateService;

    @BeforeClass
    public void beforeEachTest() {
        Group group = groupService.readGroupByName("Test");
        if (group != null) {
            groupService.deleteGroup(group.getId());
        }
    }


    @Test
    public void givenWhenCallCreateGroupThenGroupCreatedInDatabase() {

        Group group = groupService.createGroup(new Group("Test"));

        assertNotNull(group);
        assertEquals(group.getName(), "Test");

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenDuplicateNotCreatedWhenTryingToAddDuplicateGroup() {

        Group groupOriginal = groupService.createGroup(new Group("Test"));
        Group groupDuplicate = groupService.createGroup(new Group("Test"));

        assertNotNull(groupOriginal);
        assertNull(groupDuplicate);

        groupService.deleteGroup(groupOriginal.getId());
    }

    @Test
    public void givenWhenReadGroupWithExistedIdInvokeThenReturnGroup() {
        Group groupCreated = groupService.createGroup(new Group("Test"));
        Group groupReceived = groupService.readGroup(groupCreated.getId());

        assertEquals(groupCreated.getId(), groupReceived.getId());
        assertEquals(groupCreated.getName(), groupReceived.getName());

        groupService.deleteGroup(groupCreated.getId());
    }

    @Test
    public void givenWhenReadGroupWithNotExistedIdInvokeThenReturnNull() {
        assertNull(groupService.readGroup("invalid id"));
    }

    @Test
    public void givenWhenGetAllGroupsInvokeThenReturnActualGroupList() {
        int firstCallGroupsCount = groupService.readAllGroups().size();

        Group group = groupService.createGroup(new Group("Test"));

        int secondCallGroupsCount = groupService.readAllGroups().size();

        assertEquals(firstCallGroupsCount + 1, secondCallGroupsCount);

        groupService.deleteGroup(group.getId());

        int thirdCallGroupsCount = groupService.readAllGroups().size();

        assertEquals(thirdCallGroupsCount, firstCallGroupsCount);
    }

    @Test
    public void givenWhenDeleteGroupCallWithExistedGroupIdThenReturnTrue() {
        Group group = groupService.createGroup(new Group("Test"));

        assertTrue(groupService.deleteGroup(group.getId()));
    }

    @Test
    public void givenWhenDeleteGroupCallWithNotExistedGroupIdThenReturnFalse() {
        assertFalse(groupService.deleteGroup("invalid id"));
    }

    @Test
    public void givenWhenUpdateGroupWithValidParamsCallThenReturnUpdatedGroup() {
        Group group = groupService.createGroup(new Group("Test"));
        int groupsCountBeforeUpdating = groupService.readAllGroups().size();
        Group updatedGroup = groupService.updateGroup(group.getId(), new Group("Test-2"));
        int groupsCountAfterUpdating = groupService.readAllGroups().size();

        assertNotNull(updatedGroup);
        assertEquals(group.getId(), updatedGroup.getId());
        assertNotEquals(group.getName(), updatedGroup.getName());
        assertEquals(groupsCountBeforeUpdating, groupsCountAfterUpdating);

        groupService.deleteGroup(group.getId());
    }

    @Test
    public void givenWhenUpdateGroupWithNotExistedIdThenReturnNull() {
        int groupsCountBeforeUpdating = groupService.readAllGroups().size();
        assertNull(groupService.updateGroup("invalid id", new Group("Test")));
        int groupsCountAfterUpdating = groupService.readAllGroups().size();

        assertEquals(groupsCountBeforeUpdating, groupsCountAfterUpdating);
    }

    @Test
    public void givenWhenUpdateGroupWithNotUniqueNameThenReturnNull() {
        Group group1 = groupService.createGroup(new Group("Test-1"));
        Group group2 = groupService.createGroup(new Group("Test-2"));

        assertNull(groupService.updateGroup(group1.getId(), new Group("Test-2")));

        groupService.deleteGroup(group1.getId());
        groupService.deleteGroup(group2.getId());
    }

    @Test
    public void givenWhenUpdateGroupWithOtherGroupWithSameNameThenReturnUpdatedGroup() {
        Group groupCreated = groupService.createGroup(new Group("Test"));
        Group groupUpdated = groupService.updateGroup(groupCreated.getId(), new Group("Test"));

        assertNotNull(groupUpdated);
        assertEquals(groupCreated.getId(), groupUpdated.getId());
        assertEquals(groupCreated.getName(), groupUpdated.getName());

        groupService.deleteGroup(groupCreated.getId());
    }

    @Test
    public void givenWhenReadGroupByNameCallWithExistedNameThenReturnGroup() {
        Group groupCreated = groupService.createGroup(new Group("Test"));
        Group groupReceived = groupService.readGroupByName("Test");

        assertNotNull(groupReceived);
        assertEquals(groupCreated.getId(), groupReceived.getId());

        groupService.deleteGroup(groupCreated.getId());
    }

    @Test
    public void givenWhenReadGroupByNameCallWithNotExistedNameThenReturnNull() {
        assertNull(groupService.readGroupByName("invalid group name"));
    }

    @Test
    public void givenWhenGetGroupCandidatesByDateCallThenReturnListOfCandidates() {
        Group group = groupService.createGroup(new Group("Test"));

        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        Candidate candidate3 = new Candidate();
        candidate1.setDate(new GregorianCalendar(2000, 1, 1, 10, 30));
        candidate1.setFirstName("Candidate-1");

        candidate2.setDate(new GregorianCalendar(2000, 1, 2, 11, 0));
        candidate2.setFirstName("Candidate-2");

        candidate3.setDate(new GregorianCalendar(2000, 1, 1, 11, 0));
        candidate3.setFirstName("Candidate-3");

        candidate1 = candidateService.createCandidate(candidate1);
        candidate2 = candidateService.createCandidate(candidate2);
        candidate3 = candidateService.createCandidate(candidate3);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);

        group.setCandidates(candidates);

        groupService.updateGroup(group.getId(), group);

        List<Candidate> receivedCandidateList = groupService.getGroupCandidatesByDate(group.getId(),
                new GregorianCalendar(2000, 1, 2));

        assertEquals(receivedCandidateList.size(), 1);
        assertEquals(receivedCandidateList.get(0).getFirstName(), "Candidate-2");

        receivedCandidateList = groupService.getGroupCandidatesByDate(group.getId(),
                new GregorianCalendar(2000, 1, 1));

        assertEquals(receivedCandidateList.size(), 2);
        assertEquals(receivedCandidateList.get(0).getFirstName(), "Candidate-1");
        assertEquals(receivedCandidateList.get(1).getFirstName(), "Candidate-3");

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate1.getId());
        candidateService.deleteCandidate(candidate2.getId());
        candidateService.deleteCandidate(candidate3.getId());
    }

    @Test
    public void givenWhenGetGroupCandidatesByDateCallWithBadIdThenReturnNull() {
        assertNull(groupService.getGroupCandidatesByDate("invalidId", new GregorianCalendar(2000, 1, 1)));
    }

    @Test
    public void givenWhenGetGroupDatesCallThenReturnListOfDistinctDates() {
        Group group = groupService.createGroup(new Group("Test"));

        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        Candidate candidate3 = new Candidate();
        candidate1.setDate(new GregorianCalendar(2000, 1, 1, 10, 30));
        candidate2.setDate(new GregorianCalendar(2000, 1, 2, 11, 0));
        candidate3.setDate(new GregorianCalendar(2000, 1, 1, 11, 0));

        candidate1 = candidateService.createCandidate(candidate1);
        candidate2 = candidateService.createCandidate(candidate2);
        candidate3 = candidateService.createCandidate(candidate3);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);

        group.setCandidates(candidates);

        groupService.updateGroup(group.getId(), group);

        List<Calendar> groupDates = groupService.getGroupDates(group.getId());

        assertEquals(groupDates.size(), 2);

        groupService.deleteGroup(group.getId());
        candidateService.deleteCandidate(candidate1.getId());
        candidateService.deleteCandidate(candidate2.getId());
        candidateService.deleteCandidate(candidate3.getId());
    }
}