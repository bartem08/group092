package com.interview.repository;

import com.interview.model.Greeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends MongoRepository<Greeting, String> {}
