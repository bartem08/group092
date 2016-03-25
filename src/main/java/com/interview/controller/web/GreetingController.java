package com.interview.controller.web;

import com.interview.model.Greeting;
import com.interview.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    private static final String TEMPLATE = "FOR THOSE WHO DO, %s!";

    @Autowired
    private GreetingRepository greetingRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public ModelAndView greeting(@RequestParam(value = "name", required = false) String name) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", String.format(TEMPLATE, name));
        mav.setViewName("greeting");
        return mav;
    }

    @RequestMapping(value = "mongo", method = RequestMethod.GET)
    public ModelAndView mongo(@ModelAttribute("message") String message) {
        Greeting greeting = greetingRepository.findOne("1");
        message = greeting == null ? "Nothing was found" : greeting.getMessage();
        return new ModelAndView("mongo", "message", message);
    }

}
