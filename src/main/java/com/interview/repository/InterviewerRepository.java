package com.interview.repository;

import com.interview.model.Interviewer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author NSS
 */
@Repository
public interface InterviewerRepository extends MongoRepository<Interviewer, String> {}
