package com.interview.controller.rest;

import com.interview.model.Interviewer;
import com.interview.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Simple rest controller which is responsible for C.R.U.D.
 * and for interview manipulating operations:
 *      GET    - /rest/interviewers     - get all interviewers
 *      GET    - /rest/interviewers/id  - get interviewer with specified id
 *      POST   - /rest/interviewers     - add interviewer
 *      PUT    - /rest/interviewers/id  - update interviewer with specified id
 *      DELETE - /rest/interviewers/id  - delete interviewer from the repository
 *
 * Simple way to create interview with specified interviewer is to set interviewer id
 * in interviewer field
 *
 * Created by NSS on 26.03.2016.
 */

@RestController
@RequestMapping("/rest/interviewers")
public class InterviewerRestController {

    @Autowired
    private InterviewerService interviewerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readAllInterviewers() {
        final List<Interviewer> interviewers = interviewerService.readAllInterviewers();
        if (interviewers == null) {
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>(interviewers, OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity readInterviewer(@PathVariable String id) {
        final Interviewer interviewer = interviewerService.readInterviewer(id);
        if (interviewer == null) {
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>(interviewer, OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createInterviewer(@RequestBody Interviewer interviewer, BindingResult result,
                              UriComponentsBuilder uriComponentsBuilder) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        if (interviewerService.createInterviewer(interviewer) != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/interviewers/{id}").
                    buildAndExpand(interviewer.getId()).toUri());
            return new ResponseEntity<>(interviewer, headers, CREATED);
        } else {
            return new ResponseEntity<>(CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateInterviewer(@RequestBody Interviewer interviewer
            , @PathVariable("id") String id, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        interviewer.setId(id);
        if (interviewerService.readInterviewer(id) != null
                && interviewerService.updateInterviewer(interviewer) != null ) {
            return new ResponseEntity(OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInterviewer(@PathVariable("id") String id) {
        if (interviewerService.deleteInterviewer(id)) {
            return new ResponseEntity(OK);
        } else {
            return new ResponseEntity(NO_CONTENT);
        }
    }
}
