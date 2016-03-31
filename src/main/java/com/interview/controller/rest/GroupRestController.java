package com.interview.controller.rest;

import com.interview.model.Group;
import com.interview.model.Interview;
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
import java.util.List;

/**
 * Simple rest controller which is responsible for C.R.U.D.
 * and for interview manipulating operations interacting with actual database:
 *      GET    - /rest/groups           - get all groups
 *      GET    - /rest/groups/{id}      - get particular group with specified id
 *      POST   - /rest/groups           - add new group
 *      PUT    - /rest/groups/{id}      - update group with specified id
 *      DELETE - /rest/groups/{id}      - delete group with specified id
 *
 * Simple way to create interview with specified interviewer is to set interviewer id
 * in interviewer field
 *
 * @author Yegor Gulimov
 */

@RestController
@RequestMapping("/rest/groups")
public class GroupRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupService groupService;

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
}
