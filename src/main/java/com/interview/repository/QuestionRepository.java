package com.interview.repository;

import com.interview.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Kruglikov.
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {}
