package com.interview.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Web controller for Group entities
 *
 * @author Yegor Gulimov
 */

@Controller
@RequestMapping("/web/groups")
public class GroupController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping()
    public ModelAndView groups() {
        return new ModelAndView("groups", "isAdmin", hasRole("ADMIN"));
    }

    @RequestMapping("/{id}")
    public ModelAndView group(@PathVariable("id") String id) {
        return new ModelAndView("group", "groupId", id);
    }

    @RequestMapping("/{id}/report")
    public ModelAndView groupReport(@PathVariable("id") String id) {
        return new ModelAndView("groupReport", "groupId", id);
    }

    protected boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
