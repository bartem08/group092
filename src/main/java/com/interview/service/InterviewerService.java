package com.interview.service;

import com.interview.model.Group;
import com.interview.model.Interviewer;

import java.util.List;

/**
 * Created by NSS on 26.03.2016.
 */
public interface InterviewerService {

    Interviewer createInterviewer(Interviewer interviewer);

    Interviewer readInterviewer(String id);

    Interviewer findInterviewer(String login);

    List<Interviewer> readAllInterviewers();

    Interviewer updateInterviewer(Interviewer interviewer);

    boolean deleteInterviewer(String id);
}
