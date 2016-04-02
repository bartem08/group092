package com.interview.controller.web;

import com.interview.model.*;
import com.interview.service.CandidateService;
import com.interview.service.InterviewService;
import com.interview.service.InterviewerService;
import com.interview.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/interview")
public class InterviewWebController {

    @Autowired
    private InterviewService interviewService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView start(@PathVariable String id) {
        Interview interview = interviewService.readInterview(id);
        if (interview == null) {
            return new ModelAndView("failed");
        }
        System.err.print(interview.getInterviewer().getFirstName());
        ModelAndView modelAndView = new ModelAndView("interview");
        modelAndView.addObject("interview", interview);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ModelAndView finish(@PathVariable("id") String id,
                               @ModelAttribute Interview interview) {
        System.err.println("------------------------------------");
        return null;
    }
}
