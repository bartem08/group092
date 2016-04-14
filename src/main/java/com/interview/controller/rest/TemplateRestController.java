package com.interview.controller.rest;

import com.interview.model.Question;
import com.interview.model.Template;
import com.interview.service.TemplateService;
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
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * Simple rest controller which is responsible for C.R.U.D.
 * and for working with questions in template
 *
 *      GET    - /rest/templates                  - get all templates
 *      GET    - /rest/templates/id               - get template with specified id
 *      POST   - /rest/templates                  - add template
 *      PUT    - /rest/templates/id               - update template with specified id
 *      DELETE - /rest/templates/id               - delete template from the repository
 *      GET    - /rest/templates/id/questions     - get all questions from template with specified id
 *      POST   - /rest/templates/id/questions     - add question to template with specified id
 *      DELETE - /rest/templates/id/questions/id  - remove from template with specified id question with specified id
 *      GET    - /rest/templates/interviewers/id  - get all templates of the specified interviewer
 *      POST   - /rest/templates/id/clearAddList  - removes old list of questions in template and places new one instead
 *
 * @author Anton Kruglikov.
 */
@RestController
@RequestMapping("/rest/templates")
public class TemplateRestController {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateRestController.class);

    @Autowired
    TemplateService templateService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readAllTemplates(){
        final List<Template> templates = templateService.readAllTemplates();
        if (templates.isEmpty()){
            return new ResponseEntity(NO_CONTENT);
        } else {
            LOG.info("Fetching all templates: OK");
            return new ResponseEntity<>(templates, OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity readTemplate(@PathVariable("id") String id){
        final Template template = templateService.readTemplate(id);
        if(template != null){
            LOG.info("Fetching {} template: OK", id);
            return new ResponseEntity<>(template, OK);
        } else {
            LOG.error("Fetching {} template: NO_CONTENT", id);
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createTemplate(@RequestBody @Valid Template template, BindingResult result,
                                         UriComponentsBuilder uriComponentsBuilder){
        if (result.hasErrors()) {
            LOG.error("Creating template: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        if ((template = templateService.createTemplate(template)) != null) {
            LOG.info("Creating template: CREATED");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/templates/{id}").
                    buildAndExpand(template.getId()).toUri());
            return new ResponseEntity<>(template, headers, CREATED);
        } else {
            LOG.error("Creating template: CONFLICT");
            return new ResponseEntity(CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTemplate(@RequestBody @Valid Template template, BindingResult result,
                                         @PathVariable("id") String id){
        if (result.hasErrors()) {
            LOG.error("Updating template: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        template.setId(id);

        if (templateService.readTemplate(id) != null && templateService.updateTemplate(template) != null ) {
            LOG.info("Updating template: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Updating template: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplate(@PathVariable("id") String id){
        if (templateService.deleteTemplate(id)) {
            LOG.info("Deleting template: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Deleting template: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}/questions", method = RequestMethod.GET)
    public ResponseEntity getAllQuestionsFromTemplate(@PathVariable("id") String id){
        final List<Question> questions = templateService.getAllQuestionsFromTemplate(id);
        if (questions==null||questions.isEmpty()){
            return new ResponseEntity(NO_CONTENT);
        } else {
            LOG.info("Fetching all questions from template: OK");
            return new ResponseEntity<>(questions, OK);
        }
    }

    @RequestMapping(value = "/{id}/questions", method = RequestMethod.POST)
    public ResponseEntity addQuestionToTemplate(@PathVariable("id") String id, @RequestBody @Valid Question question,
                                                      BindingResult result,
                                                      UriComponentsBuilder uriComponentsBuilder){

        if (result.hasErrors()) {
            LOG.error("Adding question to template: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        if ((templateService.addQuestionToTemplate(id, question)) != null) {
            LOG.info("Adding question to template: ADDED");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/templates/{id}/questions").
                    buildAndExpand(id).toUri());
            return new ResponseEntity<>(templateService.readTemplate(id), headers, CREATED);
        } else {
            LOG.error("Adding question to template: CONFLICT");
            return new ResponseEntity(CONFLICT);
        }
    }

    @RequestMapping(value = "/{templateId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity removeQuestionFromTemplate(@PathVariable("templateId") String templateId,
                                                     @PathVariable("questionId") String questionId){

        if (templateService.deleteQuestionFromTemplate(templateId, questionId)) {
            LOG.info("Deleting question from template: OK");
            return new ResponseEntity(OK);
        } else {
            LOG.error("Deleting question from template: NO_CONTENT");
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @RequestMapping(value = "/interviewers/{interviewerId}", method = RequestMethod.GET)
    public ResponseEntity readTemplateByInterviewerId(@PathVariable("interviewerId") String interviewerId) {

        final List<Template> templates = templateService.readTemplatesByInterviewer(interviewerId);
        if (templates==null||templates.isEmpty()) {
            return new ResponseEntity(NO_CONTENT);
        } else {
            LOG.info("Fetching all templates: OK");
            return new ResponseEntity<>(templates, OK);
        }
    }

    @RequestMapping(value = "/{id}/clearAddList", method = RequestMethod.POST)
    public ResponseEntity deleteQuestionListFrTemplateAndAddNewOne(@PathVariable("id") String id,
                                                                   @RequestBody @Valid List<Question> questions,
                                                                   BindingResult result,
                                                                   UriComponentsBuilder uriComponentsBuilder){

        if (result.hasErrors()) {
            LOG.error("Adding list of questions to template: {}, BAD_REQUEST", result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), BAD_REQUEST);
        }

        if ((templateService.deleteQuestionListFrTemplateAndAddNewOne(id, questions)) != null) {
            LOG.info("Adding list of questions to template: ADDED");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/rest/templates/{id}/questions").
                    buildAndExpand(id).toUri());
            return new ResponseEntity<>(templateService.readTemplate(id), headers, CREATED);
        } else {
            LOG.error("Adding list of questions to template: CONFLICT");
            return new ResponseEntity(CONFLICT);
        }
    }
}
