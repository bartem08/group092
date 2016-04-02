package com.interview.controller.rest;

import com.interview.model.Question;
import com.interview.service.QuestionService;
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
 *
 *      GET    - /rest/questions         - get all questions
 *      GET    - /rest/questions/id      - get question with specified id
 *      POST   - /rest/questions         - add question
 *      PUT   - /rest/questions/id       - update question with specified id
 *      DELETE - /rest/questions/id      - delete question from the repository
 *
 * @author Anton Kruglikov.
 */
@RestController
@RequestMapping("/rest/questions")
public class QuestionRestController {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionRestController.class);

    @Autowired
    QuestionService questionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readAllQuestions(){
        final List<Question> questions = questionService.readAllQuestions();
        if (questions.isEmpty()){
            return new ResponseEntity(NO_CONTENT);
        } else {
            LOG.info("Fetching all questions: OK");
            return new ResponseEntity<>(questions, OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity readQuestion(@PathVariable("id") String id){
        final Question question = questionService.readQuestion(id);
        if(question != null){
            LOG.info("Fetching {} question: OK", id);
            return new ResponseEntity<>(question, OK);
        } else {
            LOG.error("Fetching {} question: NO_CONTENT", id);
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createQuestion(@RequestBody @Valid Question question, BindingResult result,
                                         UriComponentsBuilder uriComponentsBuilder){
        if (result.hasErrors()) {
            LOG.error("Creating question: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        if ((question = questionService.createQuestion(question)) != null) {
            LOG.info("Creating question: CREATED");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/questions/{id}").
                    buildAndExpand(question.getId()).toUri());
            return new ResponseEntity<>(question, headers, CREATED);
        } else {
            LOG.error("Creating question: CONFLICT");
            return new ResponseEntity(CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@RequestBody @Valid Question question, BindingResult result,
                                         @PathVariable("id") String id){
        if (result.hasErrors()) {
            LOG.error("Updating question: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        question.setId(id);

        if (questionService.readQuestion(id) != null && questionService.updateQuestion(question) != null ) {
            LOG.info("Updating question: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Updating question: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteQuestion(@PathVariable("id") String id){
        if (questionService.deleteQuestion(id)) {
            LOG.info("Deleting question: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Deleting question: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }
}
