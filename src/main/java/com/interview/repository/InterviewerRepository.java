package com.interview.repository;

import com.interview.model.Interviewer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by NSS on 26.03.2016.
 */
@Repository
public interface InterviewerRepository extends MongoRepository<Interviewer, String> {

    public Interviewer findByLogin(String login);
}
