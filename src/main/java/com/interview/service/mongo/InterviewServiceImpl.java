package com.interview.service.mongo;

import com.interview.model.Interview;
import com.interview.repository.InterviewRepository;
import com.interview.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Artem Baranovskiy
 */
@Service
public class InterviewServiceImpl implements InterviewService{

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
    public Interview updateInterview(Interview interview) {
        if (!repository.exists(interview.getId())) {
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
        return false;
    }
}
