package com.interview.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web controller for Group entities
 *
 * @author Yegor Gulimov
 */

@Controller
@RequestMapping("/web/groups")
public class GroupController {

    @RequestMapping()
    public ModelAndView groups() {
        return new ModelAndView("groups");
    }

    @RequestMapping("/{id}")
    public ModelAndView group(@PathVariable("id") String id) {
        return new ModelAndView("group", "groupId", id);
    }
}
