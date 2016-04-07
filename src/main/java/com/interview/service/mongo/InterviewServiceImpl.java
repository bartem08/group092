package com.interview.service.mongo;

import com.interview.model.Interview;
import com.interview.repository.InterviewRepository;
import com.interview.service.InterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Artem Baranovskiy
 */
@Service
public class InterviewServiceImpl implements InterviewService{

    private static final Logger LOG = LoggerFactory.getLogger(InterviewServiceImpl.class);

    @Autowired
    private InterviewRepository repository;

    @Override
    public Interview createInterview(Interview interview) {
        interview.setId(null);
        return repository.insert(interview);
    }

    @Override
    public Interview readInterview(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<Interview> readAllInterviews() {
        return repository.findAll();
    }

    @Override
    public Interview readInterviewByCandidateIdAndInterviewerId(String c_id, String i_id) {
        return repository.findByCandidateIdAndInterviewerId(c_id, i_id);
    }

    @Override
    public Interview updateInterview(Interview interview) {
        if (!repository.exists(interview.getId())) {
            LOG.error("Interview with id {} does not exist", interview.getId());
            return null;
        }
        return repository.save(interview);
    }

    @Override
    public boolean deleteInterview(String id) {
        if (repository.exists(id)) {
            repository.delete(id);
            return true;
        }
        LOG.error("Interview with id {} does not exist", id);
        return false;
    }

}
