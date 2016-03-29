package com.interview.config;

import com.interview.model.Interviewer;
import com.interview.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by NSS on 27.03.2016.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private InterviewerService interviewerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Interviewer interviewer = interviewerService.findInterviewer(username);

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(interviewer.getRole().toUpperCase()));

        User userDetail = new User(interviewer.getLogin(), interviewer.getPassword()
                , true, true, true, true, authorities);

        return userDetail;
    }
}
