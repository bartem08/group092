package com.interview.controller.rest;

import com.interview.model.Interview;
import com.interview.service.InterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Simple rest controller which is responsible for C.R.U.D.
 * and for interview manipulating operations:
 *      GET    - /rest/interviews     - get all interviews
 *      GET    - /rest/interviews/id  - get interview with specified id
 *      POST   - /rest/interviews     - add interview
 *      PUT    - /rest/interviews/id  - update interview with specified id
 *      DELETE - /rest/interviews/id  - delete interview from the repository
 *
 * Simple way to create interview with specified interviewer is to set interviewer id
 * in interviewer field
 *
 * @author Artem Baranovskiy
 */
@RestController
@RequestMapping("/rest/interviews")
public class InterviewRestController {

    private static final Logger LOG = LoggerFactory.getLogger(InterviewRestController.class);

    @Autowired
    private InterviewService interviewService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readAllInterviews() {
        final List<Interview> interviews = interviewService.readAllInterviews();
        if (interviews == null) {
            return new ResponseEntity(NO_CONTENT);
        } else {
            LOG.info("Fetching all interviews: OK");
            return new ResponseEntity<>(interviews, OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity readInterview(@PathVariable("id") String id) {
        final Interview interview = interviewService.readInterview(id);
        if (interview == null) {
            LOG.error("Fetching {} interview: NO_CONTENT", id);
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            LOG.info("Fetching {} interview: OK", id);
            return new ResponseEntity<>(interview, OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createInterview(@RequestBody @Valid Interview interview, BindingResult result,
                                          UriComponentsBuilder uriComponentsBuilder) {
        if (result.hasErrors()) {
            LOG.error("Creating interview: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        if ((interview = interviewService.createInterview(interview)) != null) {
            LOG.info("Creating interview: CREATED");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/interviews/{id}").
                    buildAndExpand(interview.getId()).toUri());
            return new ResponseEntity<>(interview, headers, CREATED);
        } else {
            LOG.error("Creating interview: CONFLICT");
            return new ResponseEntity(CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateInterview(@RequestBody @Valid Interview interview,
                                          BindingResult result,
                                          @PathVariable("id") String id) {
        if (result.hasErrors()) {
            LOG.error("Updating interview: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        interview.setId(id);
        if (interviewService.readInterview(id) != null
                && interviewService.updateInterview(interview) != null ) {
            LOG.info("Updating interview: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Updating interview: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInterview(@PathVariable("id") String id) {
        if (interviewService.deleteInterview(id)) {
            LOG.info("Deleting interview: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Deleting interview: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

}
