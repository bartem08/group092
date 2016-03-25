package com.interview.service;

import com.interview.model.Interviewer;

import java.util.List;

/**
 * @author nss
 */
public interface InterviewerService {

    Interviewer createInterviewer(Interviewer interviewer);

    Interviewer readInterviewer(String id);

    List<Interviewer> readAllInterviewers();

    Interviewer updateInterviewer(Interviewer interviewer);

    boolean deleteInterviewer(String id);

}
