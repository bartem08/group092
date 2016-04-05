package com.interview.controller.web;

import com.interview.model.*;
import com.interview.service.*;
import com.interview.util.ResultFormer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/interview")
public class InterviewWebController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView start(@PathVariable String id) {
        final Interview interview = interviewService.readInterview(id);
        if (interview == null) {
            return new ModelAndView("failed");
        }
        return new ModelAndView("interview", "interview", interview);
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public ModelAndView save(@PathVariable String id,
                             @RequestParam("template") String template,
                             @RequestParam(value = "skipped", required = false) String[] skipped,
                             @RequestParam("values") String[] values,
                             @RequestParam("comments") String comments) {

        final Interview interview = interviewService.readInterview(id);
        List<Question> questionList = templateService.readTemplate(template).getQuestions();
        Set<InterviewQuestion> questionSet = ResultFormer.formQuestionSet(questionList, values, skipped);
        interview.setQuestions(questionSet);
        interview.setComments(comments);
        interviewService.updateInterview(interview);
        return new ModelAndView("interview");
    }

}
