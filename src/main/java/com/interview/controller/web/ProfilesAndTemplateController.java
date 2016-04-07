package com.interview.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Segey Levchynskiy on 27.03.2016.
 */
@Controller
@RequestMapping("/web")
public class ProfilesAndTemplateController {
    @RequestMapping("/candidates/{id}")
    public ModelAndView candidateProfile(@PathVariable("id") String id) {
        return new ModelAndView( "profilesAndTemplate/candidateProfile", "id", id);
    }

    @RequestMapping("/interviewer")
    public ModelAndView interviewerProfile() {
        return new ModelAndView("profilesAndTemplate/interviewerProfile");
    }

    @RequestMapping("/templates")
    public ModelAndView template() {
        return new ModelAndView("profilesAndTemplate/templates");
    }
}
