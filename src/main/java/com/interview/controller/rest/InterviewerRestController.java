package com.interview.controller.rest;

import com.interview.model.Interviewer;
import com.interview.model.dto.InterviewerDTO;
import com.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * REST controller which is responsible for manipulating interviewer's informations:
 *      GET    - /rest/interviewers     - get all interviewers
 *      GET    - /rest/interviewers/id  - get interviewer with specified id
 *      GET    - /rest/interviewers/login  - get interviewer with specified login
 *      POST   - /rest/interviewers     - add interviewer
 *      PUT    - /rest/interviewers/id  - update interviewer with specified id
 *      DELETE - /rest/interviewers/id  - delete interviewer from the repository
 *
 *      POST   - /rest/interviewers/id/templates/id  - add template into the interviewer
 *      DELETE - /rest/interviewers/id/templates/id  - delete template from the interviewer
 *
 *      POST   - /rest/interviewers/id/groups/id  - add group into the interviewer
 *      DELETE - /rest/interviewers/id/groups/id  - delete group from the interviewer
 *
 * Created by NSS on 26.03.2016.
 */

@RestController
@RequestMapping("/rest/interviewers")
public class InterviewerRestController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private InterviewerService interviewerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readAllInterviewers() {
        logRequestInfo(request);
        final List<Interviewer> interviewers = interviewerService.readAllInterviewers();
        if (interviewers == null) {
            LOG.info("HTTP Status: NO_CONTENT");
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            LOG.info("HTTP Status: OK");
            return new ResponseEntity<>(interviewers, OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity readInterviewer(@PathVariable("id") String id) {
        logRequestInfo(request);
        final Interviewer interviewer = interviewerService.readInterviewer(id);
        if (interviewer == null) {
            LOG.info("HTTP Status: NO_CONTENT");
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            LOG.info("HTTP Status: OK");
            return new ResponseEntity<>(interviewer, OK);
        }
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    public ResponseEntity findInterviewer(@PathVariable("login") String login) {
        logRequestInfo(request);
        final Interviewer interviewer = interviewerService.findInterviewer(login);
        if (interviewer == null) {
            LOG.info("HTTP Status: NO_CONTENT");
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            LOG.info("HTTP Status: OK");
            return new ResponseEntity<>(interviewer, OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createInterviewer(@RequestBody Interviewer interviewer, BindingResult result,
                              UriComponentsBuilder uriComponentsBuilder) {
        logRequestInfo(request);
        if (result.hasErrors()) {
            LOG.info("HTTP Status: BAD_REQUEST");
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        if (interviewerService.createInterviewer(interviewer) != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/interviewers/{id}").
                    buildAndExpand(interviewer.getId()).toUri());
            LOG.info("HTTP Status: CREATED");
            return new ResponseEntity<>(interviewer, headers, CREATED);
        } else {
            LOG.info("HTTP Status: CONFLICT");
            return new ResponseEntity<>(CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateInterviewer(@RequestBody Interviewer interviewer
            , @PathVariable("id") String id, BindingResult result) {
        logRequestInfo(request);
        if (result.hasErrors()) {
            LOG.info("HTTP Status: BAD_REQUEST");
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }
        interviewer.setId(id);
        if (interviewerService.readInterviewer(id) != null
                && interviewerService.updateInterviewer(interviewer) != null ) {
            LOG.info("HTTP Status: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.info("HTTP Status: CONFLICT");
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInterviewer(@PathVariable("id") String id) {
        logRequestInfo(request);
        if (interviewerService.deleteInterviewer(id)) {
            LOG.info("HTTP Status: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.info("HTTP Status: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{login}/groups/dto", method = RequestMethod.GET)
    public ResponseEntity findInterviewerDTO(@PathVariable("login") String login) {
        logRequestInfo(request);
        final Interviewer interviewer = interviewerService.findInterviewer(login);
        if (interviewer == null) {
            LOG.info("HTTP Status: NO_CONTENT");
            return new ResponseEntity<>(NO_CONTENT);
        }

        InterviewerDTO interviewerDTO = new InterviewerDTO(interviewer);

        LOG.info("HTTP Status: OK");
        return new ResponseEntity<>(interviewerDTO, OK);
    }

    private void logRequestInfo(HttpServletRequest request) {
        Enumeration headers = request.getHeaderNames();
        StringBuilder builder = new StringBuilder("\n");
        while (headers.hasMoreElements()) {
            String key = (String) headers.nextElement();
            builder.append("- ").
                    append(key).
                    append(": ").
                    append(request.getHeader(key)).
                    append("\n");
        }
        LOG.info(String.format(
                "--------------------\n" +
                        "Request URL: %s\n" +
                        "Request Method: %s\n" +
                        "Request Headers: %s",
                request.getRequestURL(),
                request.getMethod(),
                builder.toString()));
    }
}
