package com.interview.service.mongo;

import com.interview.model.Interviewer;
import com.interview.repository.InterviewerRepository;
import com.interview.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NSS on 27.03.2016.
 */
@Service
public class InterviewerServiceImpl  implements InterviewerService {

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Override
    public Interviewer readInterviewer(String id) {
        return interviewerRepository.findOne(id);
    }

    @Override
    public Interviewer findInterviewer(String login) {
        return interviewerRepository.findByLogin(login);
    }

    @Override
    public List<Interviewer> readAllInterviewers() {
        return interviewerRepository.findAll();
    }

    @Override
    public Interviewer createInterviewer(Interviewer interviewer) {
        return interviewerRepository.save(interviewer);
    }

    @Override
    public Interviewer updateInterviewer(Interviewer interviewer) {
        if (! interviewerRepository.exists(interviewer.getId())) {
            return null;
        }
        return interviewerRepository.save(interviewer);
    }

    @Override
    public boolean deleteInterviewer(String id) {
        if (interviewerRepository.exists(id)) {
            interviewerRepository.delete(id);
            return true;
        }
        return false;
    }
}
