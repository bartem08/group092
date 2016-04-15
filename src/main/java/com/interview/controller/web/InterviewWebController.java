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
                              @RequestParam(value = "group_name", required = false) String groupName) {
        final Interview interview = interviewService.readInterview(id);
        final Template template = templateService.readTemplate(template_id);
        if (interview.getResult() == 0) {
            interview.setQuestions(ResultFormer.formQuestionList(template.getQuestions()));
            interviewService.updateInterview(interview);
        }
        return formModelAndView(id, groupName, template.getName());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("info") String[] info,
                             @RequestParam("values") String[] values,
                             @RequestParam(value = "skipped", required = false) String[] skipped,
                             @RequestParam(value = "comments", required = false) String comments) {

        final Interview interview = interviewService.readInterview(info[2]);
        if (interview.getResult() == 0) {
            interview.setQuestions(ResultFormer.formQuestionList(interview.getQuestions(), Arrays.asList(values), skipped));
        }
        interview.setComments(comments);
        interviewService.updateInterview(interview);
        return formModelAndView(info[2], info[0], info[1]);
    }

    private ModelAndView formModelAndView(final String interview_id,  String groupName, String templateName) {
        return new ModelAndView("interview", "interview_id", interview_id)
                .addObject("group_name", groupName)
                .addObject("template_name", templateName);
    }

}
