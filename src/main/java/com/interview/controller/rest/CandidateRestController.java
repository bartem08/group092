package com.interview.controller.rest;

import com.interview.model.Candidate;
import com.interview.model.Interview;
import com.interview.service.CandidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/candidates")
public class CandidateRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CandidateService candidateService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCandidate(@RequestBody Candidate candidate) {
        try {
            candidateService.createCandidate(candidate);
            log.info("HttpStatus: CREATED");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("HttpStatus: BAD_REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Candidate> readCandidate(@PathVariable("id") String id) {
        Candidate candidate = candidateService.readCandidate(id);

        if (candidate != null) {
            log.info("HttpStatus: OK");
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        }
        log.error("HttpStatus: NO_CONTENT");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") String id) {
        if (candidateService.deleteCandidate(id)) {
            log.info("HttpStatus: OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.error("HttpStatus: NO_CONTENT");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCandidate(@RequestBody Candidate candidate, @PathVariable("id") String id) {
        candidate.setId(id);

        if (candidateService.updateCandidate(candidate) != null) {
            log.info("HttpStatus: OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.error("HttpStatus: NO_CONTENT");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Candidate>> candidateList() {
        List<Candidate> candidates = candidateService.readAllCandidates();

        if (candidates.isEmpty()) {
            log.error("HttpStatus: NO_CONTENT");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("HttpStatus: OK");
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> clearCandidate() {
        candidateService.deleteAllCandidates();
        log.info("HttpStatus: OK");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{idCandidate}/interviews/{idInterview}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addInterviewToCandidate(@PathVariable("idCandidate") String idCandidate, @PathVariable("idInterview") String idInterview) {
        if(candidateService.addInterviewToCandidate(idCandidate,idInterview)){
            log.info("HttpStatus: OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("HttpStatus: BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{idCandidate}/interviews/{idInterview}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeInterviewFromCandidate(@PathVariable("idCandidate") String idCandidate, @PathVariable("idInterview") String idInterview) {
        if(candidateService.removeInterviewFromCandidate(idCandidate, idInterview)){
            log.info("HttpStatus: OK");
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("HttpStatus: BAD_REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{idCandidate}/interviews/{idInterview}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Interview> getInterviewOfCandidate(@PathVariable("idCandidate") String idCandidate, @PathVariable("idInterview") String idInterview){
        Interview interview = candidateService.getInterviewOfCandidate(idCandidate,idInterview);

        if(interview != null){
            return new ResponseEntity<>(interview,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/interviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Interview>> getInterviewListOfCandidate(@PathVariable("id") String id){
        List<Interview> interviewList = candidateService.getInterviewListOfCandidate(id);

        if (!interviewList.isEmpty()){
            return new ResponseEntity<>(interviewList,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
