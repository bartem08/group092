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

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ModelAndView start(@PathVariable String id,
                              @RequestParam(value = "template_id") String template_id,
                              @RequestParam(value = "group_name", required = false) String group) {
        final Interview interview = interviewService.readInterview(id);
        final Template template = templateService.readTemplate(template_id);
        if (interview.getQuestions() == null || interview.getQuestions().isEmpty()) {
            interview.setQuestions(ResultFormer.formQuestionList(template.getQuestions()));
            interviewService.updateInterview(interview);
        }
        ModelAndView modelAndView = new ModelAndView("interview");
        modelAndView.addObject("interview_id", interview.getId());
        modelAndView.addObject("group_name", group);
        modelAndView.addObject("template_name", template.getName());
        return modelAndView;
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public ModelAndView save(@PathVariable("id") String id,
                             @RequestParam(value = "skipped", required = false) String[] skipped,
                             @RequestParam("values") String[] values,
                             @RequestParam("comments") String comments) {

        final Interview interview = interviewService.readInterview(id);
        interview.setQuestions(ResultFormer.formQuestionList(interview.getQuestions(), Arrays.asList(values), skipped));
        interview.setComments(comments);
        interviewService.updateInterview(interview);
        return new ModelAndView("groups");
    }

}
