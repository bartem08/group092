package com.interview.service;

import com.interview.model.Interview;

import java.util.List;

/**
 * @author Artem Baranovskiy
 */
public interface InterviewService {

    Interview createInterview(Interview interview);

    Interview readInterview(String id);

    List<Interview> readAllInterviews();

    Interview updateInterview(Interview interview);

    boolean deleteInterview(String id);

}
