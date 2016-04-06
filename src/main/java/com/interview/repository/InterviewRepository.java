package com.interview.repository;

import com.interview.model.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Artem Baranovskiy
 */
@Repository
public interface InterviewRepository extends MongoRepository<Interview, String> {
    Interview findByCandidateIdAndInterviewerId(String c_id, String i_id);
}
