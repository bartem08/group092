package com.interview.controller.web;

import com.interview.model.Greeting;
import com.interview.model.Interviewer;
import com.interview.repository.GreetingRepository;
import com.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/web")
public class InterviewerWebController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InterviewerService interviewerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        model.setViewName("redirect:/web/login?logout");
        return model;
    }

    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    public ModelAndView loginError() {
        ModelAndView model = new ModelAndView();
        model.addObject("error", "true");
        model.setViewName("failed");
        return model;
    }

    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
            model.addObject("message", " Sorry. You don't have privilages to view this page!!!");
        }
        model.setViewName("denied");
        return model;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView showInterviewer() {
        ModelAndView model = new ModelAndView();
        model.setViewName("groups");
        return model;
    }

    @RequestMapping(value = "/interviewers", method = RequestMethod.GET)
    public ModelAndView showInterviewers() {
        ModelAndView model = new ModelAndView();
        model.setViewName("interviewer/list");
        return model;
    }

    @RequestMapping(value = "/interviewers/{id}", method = RequestMethod.GET)
    public ModelAndView showInterviewer(@PathVariable("id") String id) {
        Interviewer interviewer = interviewerService.readInterviewer(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("interviewer/show");
        return model;
    }

    @RequestMapping(value = "/interviewers/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteInterviewer(@PathVariable("id") String id) {
        Interviewer interviewer = interviewerService.readInterviewer(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("interviewer/list");
        return model;
    }

    @RequestMapping(value = "/interviewers/{id}", method = RequestMethod.PUT)
    public ModelAndView updateInterviewer(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        model.setViewName("interviewer/edit");
        return model;
    }


    @RequestMapping(value = "/interviewers", method = RequestMethod.POST)
    public ModelAndView addInterviewer(@ModelAttribute("interviewer") Interviewer interviewer) {
        ModelAndView model = new ModelAndView();
        model.setViewName("interviewer/create");
        return model;
    }
}
