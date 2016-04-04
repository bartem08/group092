package com.interview.controller.rest;

import com.interview.model.Candidate;
import com.interview.model.Group;
import com.interview.model.Interview;
import com.interview.model.dto.GroupDTO;
import com.interview.model.dto.GroupDayDTO;
import com.interview.service.CandidateService;
import com.interview.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.*;

/**
 * REST controller for entity Group
 * Basic CRUD methods:
 *      GET    - /rest/groups           - get all groups
 *      GET    - /rest/groups/{id}      - get particular group with specified id
 *      POST   - /rest/groups           - add new group
 *      PUT    - /rest/groups/{id}      - update group with specified id
 *      DELETE - /rest/groups/{id}      - delete group with specified id
 *
 * Interacting with groups through DTO:
 *      GET    - /rest/groups/dto       - get all group DTO
 *      GET    - /rest/groups/{id}/dto  - get particular group DTO with specified id
 *      GET    - /rest/groups/{id}/days - get list of GroupDayDTO
 *
 * Managing candidates:
 *      PUT    - /rest/groups/{groupId}/addCandidate/{candidateId}      - add candidate to group
 *      PUT    - /rest/groups/{groupId}/removeCandidate/{candidateId}   - remove candidate from group
 *
 * @author Yegor Gulimov
 */

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

    @Autowired
    private CandidateService candidateService;

    @RequestMapping()
    public ResponseEntity readAllGroups() {

        List<Group> groups = groupService.readAllGroups();

        if (groups.isEmpty()) {
            log.error("Fetching all groups: NO CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Fetching all groups: OK");
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity readGroup(@PathVariable("id") String id) {

        Group group = groupService.readGroup(id);

        if (group == null) {
            log.error("Fetching '{}' group: ", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Fetching '{}' group: ", id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody @Valid Group group,
                                             BindingResult result,
                                             UriComponentsBuilder builder) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();

            log.error("Creating group: {}, BAD REQUEST", message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if (groupService.createGroup(group) != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/rest/groups/{id}").
                    buildAndExpand(group.getId()).toUri());

            log.info("Creating group: '{}', CREATED", group.getId());
            return new ResponseEntity<>(group, HttpStatus.CREATED);
        }

        log.info("Creating group: CONFLICT");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@RequestBody @Valid Group group,
                                            BindingResult result,
                                            @PathVariable("id") String id) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();

            log.error("Updating interview: {}, BAD REQUEST", message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        group.setId(id);
        if (groupService.readGroup(id) != null
                && groupService.updateGroup(id, group) != null ) {
            log.info("Updating group: '{}', OK", id);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } else {
            log.error("Updating group: NO CONTENT");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInterview(@PathVariable("id") String id) {
        if (groupService.deleteGroup(id)) {
            log.info("Deleting '{}' group: OK", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Deleting '{}' group: NO CONTENT", id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    //Methods for fetching group DTO instead of actual group

    @RequestMapping("/dto")
    public ResponseEntity readAllGroupsDto() {
        List<Group> actualGroups = groupService.readAllGroups();

        if (actualGroups.isEmpty()) {
            log.error("Fetching all group DTO: NO CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<GroupDTO> dtoGroups = new ArrayList<>();

        for (Group actualGroup : actualGroups) {
            dtoGroups.add(new GroupDTO(actualGroup));
        }

        Collections.sort(dtoGroups);    //get groups DTO sorted in alphabetic order

        log.info("Fetching all group DTO: OK");
        return new ResponseEntity<>(dtoGroups, HttpStatus.OK);
    }

    @RequestMapping("/{id}/dto")
    public ResponseEntity readGroupDto(@PathVariable("id") String id) {
        Group actualGroup = groupService.readGroup(id);

        if (actualGroup == null) {
            log.error("Fetching group '{}' DTO: NO CONTENT", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Fetching group '{}' DTO: OK", id);
        return new ResponseEntity<>(new GroupDTO(actualGroup), HttpStatus.OK);
    }

    //Managing candidates

    @RequestMapping(value = "/{groupId}/addCandidate/{candidateId}", method = RequestMethod.PUT)
    public ResponseEntity addCandidateToGroup(@PathVariable("groupId") String groupId,
                                              @PathVariable("candidateId") String candidateId) {
        boolean validRequest = true;

        Group group = groupService.readGroup(groupId);
        if (group == null) {
            log.error("Group with id '{}' doesn't exist in database", groupId);
            validRequest = false;
        }

        Candidate candidate = candidateService.readCandidate(candidateId);
        if (candidate == null) {
            log.error("Candidate with id '{}' doesn't exist in database", candidateId);
            validRequest = false;
        }

        if (!validRequest) {
            log.error("Adding candidate to group: BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Candidate> candidates = group.getCandidates();
        for (Candidate c : candidates) {
            if (c.getId().equals(candidateId)) {
                log.error("Cannot add candidate to group. Group '{}' with id '{}' already contains candidate with id '{}'",
                        group.getName(), groupId, candidateId);
                log.error("Adding candidate to group: CONFLICT");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        candidates.add(candidate);
        group.setCandidates(candidates);
        group = groupService.updateGroup(groupId, group);

        log.info("Candidate with id '{}' has been added to group '{}' with id '{}'",
                candidateId, group.getName(), groupId);
        log.info("Adding candidate to group: OK");
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}/removeCandidate/{candidateId}", method = RequestMethod.PUT)
    public ResponseEntity removeCandidateFromGroup(@PathVariable("groupId") String groupId,
                                              @PathVariable("candidateId") String candidateId) {
        boolean validRequest = true;

        Group group = groupService.readGroup(groupId);
        if (group == null) {
            log.error("Group with id '{}' doesn't exist in database", groupId);
            validRequest = false;
        }

        Candidate candidate = candidateService.readCandidate(candidateId);
        if (candidate == null) {
            log.error("Candidate with id '{}' doesn't exist in database", candidateId);
            validRequest = false;
        }

        if (!validRequest) {
            log.error("Removing candidate from group: BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Candidate> candidates = group.getCandidates();
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).getId().equals(candidateId)) {
                candidates.remove(i);
                group.setCandidates(candidates);
                group = groupService.updateGroup(groupId, group);

                log.info("Candidate with id '{}' has been removed from group '{}' with id '{}'",
                        candidateId, group.getName(), groupId);
                log.info("Removing candidate from group: OK");
                return new ResponseEntity<>(group, HttpStatus.OK);
            }
        }

        log.error("Cannot remove candidate from group. Group '{}' with id '{}' doesn't contain candidate with id '{}'",
                group.getName(), groupId, candidateId);
        log.error("Removing candidate from group: CONFLICT");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping("/{id}/days")
    public ResponseEntity getGroupDays(@PathVariable("id") String id) {
        List<Calendar> dayDates = groupService.getGroupDates(id);
        if (dayDates == null) {
            log.error("Getting group days: BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<GroupDayDTO> groupDayDTOList = new ArrayList<>();
        if (dayDates.isEmpty()) {
            log.info("Getting group days: NO CONTENT");
            return new ResponseEntity<>(groupDayDTOList, HttpStatus.NO_CONTENT);
        }

        for (Calendar dayDate : dayDates) {
            groupDayDTOList.add(new GroupDayDTO(groupService.getGroupCandidatesByDate(id, dayDate), dayDate));
        }

        Collections.sort(groupDayDTOList);

        log.info("{} days found for group with id '{}'", dayDates.size(), id);
        log.info("Getting group days: OK");
        return new ResponseEntity<>(groupDayDTOList, HttpStatus.OK);
    }

}
